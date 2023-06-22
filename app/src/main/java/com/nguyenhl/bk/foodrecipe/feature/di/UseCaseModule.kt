package com.nguyenhl.bk.foodrecipe.feature.di

import com.nguyenhl.bk.foodrecipe.feature.presentation.main.calendar.CalendarUseCase
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.usecase.HomeFetchRecipeUseCase
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.usecase.HomeUseCase
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.usecase.SearchUseCase
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.usecase.SearchFilterUseCase
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.usecase.SearchMealUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { HomeUseCase(get(), get()) }
    single { HomeFetchRecipeUseCase(get(), get(), get(), get(), get(), get()) }
    single { SearchFilterUseCase(get(), get()) }
    single { SearchUseCase(get(), get(), get()) }
    single { CalendarUseCase(get()) }
    single { SearchMealUseCase(get()) }
}
