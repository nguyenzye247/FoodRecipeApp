package com.nguyenhl.bk.foodrecipe.feature.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.nguyenhl.bk.foodrecipe.core.extension.views.setMarginBottom
import com.nguyenhl.bk.foodrecipe.core.extension.views.setMarginTop
import com.nguyenhl.bk.foodrecipe.feature.util.AppUtil
import io.reactivex.rxjava3.disposables.CompositeDisposable

abstract class BaseFragment<T : ViewBinding, V : BaseViewModel> : Fragment() {
    protected val subscription: CompositeDisposable = CompositeDisposable()
    var isCreated = false
    abstract fun getLazyBinding(): Lazy<T>
    abstract fun getLazyViewModel(): Lazy<V>
    open fun setupInit() {
        initViews()
        initListener()
        initObservers()
    }

    protected val binding by getLazyBinding()
    protected val viewModel by getLazyViewModel()

    abstract fun initViews()
    abstract fun initListener()
    abstract fun initObservers()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCreated = true
        setupInit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isCreated = false
    }

    override fun onDestroy() {
        super.onDestroy()
        subscription.clear()
    }

    fun adjustScreenSize(view: View) {
        binding.apply {
            ViewCompat.setOnApplyWindowInsetsListener(
                view
            ) { v, insets ->
                val marginTop = insets.systemWindowInsetTop()
                val marginBottom = insets.systemWindowInsetBottom()
                view.setMarginTop(marginTop)
                view.setMarginBottom(marginBottom)
                insets
            }
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
}