package com.nguyenhl.bk.foodrecipe.feature.di

import com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.usecase.HomeFetchRecipeUseCase
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.usecase.HomeUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { HomeUseCase(get(), get()) }
    single { HomeFetchRecipeUseCase(get(), get()) }
}
