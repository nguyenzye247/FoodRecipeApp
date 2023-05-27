package com.nguyenhl.bk.foodrecipe.feature.di

import com.nguyenhl.bk.foodrecipe.feature.data.repository.*
import com.nguyenhl.bk.foodrecipe.feature.data.repository.auth.ForgotPasswordRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.auth.LoginRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.auth.RegisterRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { RegisterRepository(get()) }
    single { LoginRepository(get()) }
    single { ForgotPasswordRepository(get()) }
    single { UserRepository(get()) }
    single { UserInfoRepository(get()) }
    single { HealthStatusRepository(get(), get(), get()) }
    single { CategoryRepository(get(), get(), get()) }
    single { DishPreferredRepository(get(), get()) }
}
