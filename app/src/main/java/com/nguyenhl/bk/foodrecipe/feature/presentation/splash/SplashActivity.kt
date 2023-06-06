package com.nguyenhl.bk.foodrecipe.feature.presentation.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.asLiveData
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observeDistinct
import com.nguyenhl.bk.foodrecipe.databinding.ActivitySplashBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.ViewModelProviderFactory
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.AuthStatus
import com.nguyenhl.bk.foodrecipe.feature.presentation.auth.login.LoginActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.createdishprefered.DishPreferredActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.MainActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.MainViewModel
import com.nguyenhl.bk.foodrecipe.feature.util.AppUtil
import kotlinx.coroutines.Dispatchers
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    override fun getLazyBinding() = lazy { ActivitySplashBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModel<SplashViewModel> {
        parametersOf(BaseInput.SplashInput(application))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        if (AppUtil.isAndroid_TIRAMISU_AndAbove()) {
            // Keep the splash screen visible for this Activity
            splashScreen.setKeepOnScreenCondition { true }
        }
    }

    override fun initViews() {
        hideStatusAndNavigationBar()
        binding.apply {
            // TODO: load app logo drawable
//            ivAppLogo.loadImage()
        }
    }

    override fun initListener() {

    }

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    override fun initObservers() {
        val isLoggedIn = viewModel.doOnUserLoggedIn {
            observeDistinct(viewModel.liveIsValidUserInfo()) { isValid ->
                isValid?.let {
                    when (isValid) {
                        AuthStatus.VALID -> {
                            goToMain()
                        }
                        AuthStatus.INVALID -> {
                            goToDishPreferred()
                        }
                        AuthStatus.EXPIRED -> {
                            goToLogin()
                        }
                    }
                } ?: run {
                    goToLogin()
                }
            }
        }
        if (isLoggedIn) return
        observeDistinct(viewModel.isFinish.asLiveData(Dispatchers.Main)) { isFinish ->
            isFinish ?: return@observeDistinct
            if (!isFinish) return@observeDistinct
            goToLogin()
        }
    }

    private fun goToLogin() {
        LoginActivity.startActivity(this) {
            // put stuffs
        }
    }

    private fun goToMain() {
        MainActivity.startActivity(this) {
            // put stuffs
        }
    }

    private fun goToDishPreferred() {
        DishPreferredActivity.startActivity(this) {
            // put stuffs
        }
    }
}