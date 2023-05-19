package com.nguyenhl.bk.foodrecipe.feature.di

import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.ViewModelProviderFactory
import com.nguyenhl.bk.foodrecipe.feature.presentation.authentication.register.RegisterViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
//    viewModel {
//        (input: BaseInput.RegisterInput) -> RegisterViewModel(input, get())
//    }

    viewModel {
        (input: BaseInput.RegisterInput) -> ViewModelProviderFactory(input).create(RegisterViewModel::class.java)
    }
}