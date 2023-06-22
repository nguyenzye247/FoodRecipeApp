package com.nguyenhl.bk.foodrecipe.feature.di

import com.nguyenhl.bk.foodrecipe.feature.data.repository.*
import com.nguyenhl.bk.foodrecipe.feature.data.repository.auth.ForgotPasswordRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.auth.LoginRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.auth.RegisterRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.search.SearchFilterRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.search.SearchRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { RegisterRepository(get()) }
    single { LoginRepository(get()) }
    single { ForgotPasswordRepository(get()) }
    single { UserRepository(get()) }
    single { UserInfoRepository(get(), get()) }
    single { HealthStatusRepository(get(), get(), get()) }
    single { CategoryRepository(get(), get(), get()) }
    single { DishPreferredRepository(get(), get()) }
    single { RecipeRepository(get(), get()) }
    single { CollectionRepository(get(), get()) }
    single { AuthorRepository(get()) }
    single { IngredientRepository(get()) }
    single { SearchFilterRepository(get()) }
    single { SearchRepository(get()) }
    single { CalendarRepository(get()) }
}
