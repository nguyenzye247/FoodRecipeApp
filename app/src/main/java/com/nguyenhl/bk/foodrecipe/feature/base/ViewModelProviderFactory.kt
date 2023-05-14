package com.nguyenhl.bk.foodrecipe.feature.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nguyenhl.bk.foodrecipe.feature.ui.authentication.createaccount.CreateAccountViewModel
import com.nguyenhl.bk.foodrecipe.feature.ui.authentication.createinfo.CreateInfoViewModel
import com.nguyenhl.bk.foodrecipe.feature.ui.authentication.forgot.ForgotPasswordViewModel
import com.nguyenhl.bk.foodrecipe.feature.ui.authentication.login.LoginViewModel
import com.nguyenhl.bk.foodrecipe.feature.ui.authentication.register.RegisterViewModel
import com.nguyenhl.bk.foodrecipe.feature.ui.dishprefered.DishPreferredViewModel
import com.nguyenhl.bk.foodrecipe.feature.ui.main.MainViewModel
import com.nguyenhl.bk.foodrecipe.feature.ui.splash.SplashViewModel

class ViewModelProviderFactory(private val input: BaseInput) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) -> {
                return SplashViewModel(input as BaseInput.NoInput) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                return MainViewModel(input as BaseInput.MainInput) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                return LoginViewModel(input as BaseInput.LoginInput) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                return RegisterViewModel(input as BaseInput.RegisterInput) as T
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
                return CreateInfoViewModel(input as BaseInput.CreateInfoInput) as T
            }

        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
