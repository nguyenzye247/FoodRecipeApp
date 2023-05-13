package com.nguyenhl.bk.foodrecipe.feature.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nguyenhl.bk.foodrecipe.feature.ui.authentication.login.LoginViewModel
import com.nguyenhl.bk.foodrecipe.feature.ui.authentication.register.RegisterViewModel
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

        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
