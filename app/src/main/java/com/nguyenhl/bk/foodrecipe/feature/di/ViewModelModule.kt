package com.nguyenhl.bk.foodrecipe.feature.di

import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.ViewModelProviderFactory
import com.nguyenhl.bk.foodrecipe.feature.presentation.auth.forgot.ForgotPasswordViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.createinfo.CreateInfoViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.auth.login.LoginViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.auth.register.RegisterViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.createdishprefered.DishPreferredViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { (input: BaseInput.RegisterInput) ->
        ViewModelProviderFactory(input).create(RegisterViewModel::class.java)
    }

    viewModel { (input: BaseInput.LoginInput) ->
        ViewModelProviderFactory(input).create(LoginViewModel::class.java)
    }

    viewModel { (input: BaseInput.ForgotPasswordInput) ->
        ViewModelProviderFactory(input).create(ForgotPasswordViewModel::class.java)
    }

    viewModel { (input: BaseInput.CreateInfoInput) ->
        ViewModelProviderFactory(input).create(CreateInfoViewModel::class.java)
    }

    viewModel { (input: BaseInput.DishPreferredInput) ->
        ViewModelProviderFactory(input).create(DishPreferredViewModel::class.java)
    }
}