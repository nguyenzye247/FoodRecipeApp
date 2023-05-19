package com.nguyenhl.bk.foodrecipe.feature.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.views.setPaddingBottom
import com.nguyenhl.bk.foodrecipe.core.extension.views.setPaddingTop
import com.nguyenhl.bk.foodrecipe.feature.presentation.authentication.login.LoginActivity
import com.nguyenhl.bk.foodrecipe.feature.util.AppUtil
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseActivity<T : ViewBinding, V : BaseViewModel> : AppCompatActivity() {
    protected val subscription: CompositeDisposable = CompositeDisposable()
    abstract fun getLazyBinding(): Lazy<T>
    abstract fun getLazyViewModel(): Lazy<V>
    open fun setupInit() {
        initViews()
        initListener()
        initObservers()
    }

    protected val binding by getLazyBinding()
    protected val viewModel by getLazyViewModel()
    private var networkCallback: ConnectivityManager.NetworkCallback? = null

    abstract fun initViews()
    abstract fun initListener()
    abstract fun initObservers()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupInit()
        makeStatusBarLight()
    }

    override fun onDestroy() {
        super.onDestroy()
        subscription.clear()
        unregisterNetworkCallback()
    }

    fun adjustScreenSize(view: View) {
        binding.apply {
            ViewCompat.setOnApplyWindowInsetsListener(
                view
            ) { v, insets ->
                val marginTop = insets.systemWindowInsetTop()
                val marginBottom = insets.systemWindowInsetBottom()
                root.setPaddingTop(marginTop)
                root.setPaddingBottom(marginBottom)
                insets
            }
        }
    }

    fun makeStatusBarTransparent() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
    }

    fun setStatusBarBlack() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//        window.statusBarColor = getColor(R.color.black_160)
    }

    fun hideStatusAndNavigationBar() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        window.statusBarColor = Color.TRANSPARENT
    }

    fun hideNavigationBar() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                )
    }

    fun hideSystemStatusBar() {
        // Hide the status bar.
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
    }

    fun makeStatusBarLight() {
        window.decorView.systemUiVisibility =
            (View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)

        // We can only set light nav bar on API 27 in attrs, but we can do it in API 26 here
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
    }

    @SuppressLint("WrongConstant")
    fun WindowInsetsCompat.systemWindowInsetTop(): Int = when {
        AppUtil.isAndroid_TIRAMISU_AndAbove() -> getInsets(WindowInsets.Type.statusBars()).top
        else -> @Suppress("DEPRECATION") systemWindowInsetTop
    }

    @SuppressLint("WrongConstant")
    fun WindowInsetsCompat.systemWindowInsetBottom(): Int = when {
        AppUtil.isAndroid_TIRAMISU_AndAbove() -> getInsets(WindowInsets.Type.navigationBars()).bottom
        else -> @Suppress("DEPRECATION") systemWindowInsetBottom
    }

    fun rotateScreen() {
        requestedOrientation = if (isScreenInPortraitMode()) {
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        } else {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    fun getStatusBarHeight(): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    private fun getNetworkCallback(
        onAvailableCallback: () -> Unit,
        onLostCallback: () -> Unit,
    ): ConnectivityManager.NetworkCallback {
        return object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                onAvailableCallback.invoke()
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                onLostCallback.invoke()
            }
        }
    }

    private fun getConnectivityManager() =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private fun unregisterNetworkCallback() {
        networkCallback?.let {
            getConnectivityManager().unregisterNetworkCallback(it)
        }
    }

    protected fun isScreenInPortraitMode(): Boolean {
        val currentOrientation = resources.configuration.orientation
        return currentOrientation == Configuration.ORIENTATION_PORTRAIT
    }

//    protected fun listenNetworkChange(
//        onAvailableCallback: () -> Unit,
//        onLostCallback: () -> Unit,
//    ) {
//        val connectivityManager = getConnectivityManager()
//        if (networkCallback == null) {
//            networkCallback = getNetworkCallback(
//                onAvailableCallback,
//                onLostCallback
//            )
//        }
//        if (isAndroid_N_AndAbove()) {
//            networkCallback?.let {
//                connectivityManager.registerDefaultNetworkCallback(it)
//            }
//        } else {
//            networkCallback?.let {
//                val request = NetworkRequest.Builder()
//                    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()
//                connectivityManager.registerNetworkCallback(request, it)
//            }
//        }
//    }

//    protected fun onInternetConnected(isConnected: Boolean, onConnected: () -> Unit) {
//        if (!isConnected) {
//            showToast(getString(R.string.network_not_connected_toast))
//            return
//        }
//        onConnected.invoke()
//    }

//    protected fun isNetworkAvailable() =
//        NetworkUtils.isNetworkAvailable(getConnectivityManager())
}
