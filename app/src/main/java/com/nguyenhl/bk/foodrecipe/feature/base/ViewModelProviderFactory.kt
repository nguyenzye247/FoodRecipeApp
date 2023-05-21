package com.nguyenhl.bk.foodrecipe.feature.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nguyenhl.bk.foodrecipe.feature.data.repository.HealthStatusRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.LoginRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.RegisterRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.UserInfoRepository
import com.nguyenhl.bk.foodrecipe.feature.presentation.authentication.createaccount.CreateAccountViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.authentication.createinfo.CreateInfoViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.authentication.forgot.ForgotPasswordViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.authentication.login.LoginViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.authentication.register.RegisterViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.dishprefered.DishPreferredViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.MainViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.splash.SplashViewModel
import org.koin.core.KoinComponent
import org.koin.core.inject

class ViewModelProviderFactory(private val input: BaseInput) : ViewModelProvider.Factory,
    KoinComponent {
    private val registerRepository: RegisterRepository by inject()
    private val loginRepository: LoginRepository by inject()
    private val userInfoRepository: UserInfoRepository by inject()
    private val healthStatusRepository: HealthStatusRepository by inject()

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                return SplashViewModel(
                    input as BaseInput.NoInput,
                    healthStatusRepository
                ) as T
            }

            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                return MainViewModel(input as BaseInput.MainInput) as T
            }

            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                return LoginViewModel(
                    input as BaseInput.LoginInput,
                    loginRepository,
                    userInfoRepository
                ) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                return RegisterViewModel(
                    input as BaseInput.RegisterInput,
                    registerRepository
                ) as T
            }

            modelClass.isAssignableFrom(CreateAccountViewModel::class.java) -> {
                return CreateAccountViewModel(input as BaseInput.CreateAccountInput) as T
            }

            modelClass.isAssignableFrom(ForgotPasswordViewModel::class.java) -> {
                return ForgotPasswordViewModel(input as BaseInput.ForgotPasswordInput) as T
            }

            modelClass.isAssignableFrom(DishPreferredViewModel::class.java) -> {
                return DishPreferredViewModel(input as BaseInput.DishPreferredInput) as T
            }

            modelClass.isAssignableFrom(CreateInfoViewModel::class.java) -> {
                return CreateInfoViewModel(
                    input as BaseInput.CreateInfoInput,
                    userInfoRepository,
                    healthStatusRepository
                ) as T
            }

        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
