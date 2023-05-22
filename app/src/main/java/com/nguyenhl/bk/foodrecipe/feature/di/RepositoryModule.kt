package com.nguyenhl.bk.foodrecipe.feature.di

import com.nguyenhl.bk.foodrecipe.feature.data.repository.*
import org.koin.dsl.module

val repositoryModule = module {
    single { RegisterRepository(get()) }
    single { LoginRepository(get()) }
    single { UserInfoRepository(get()) }
    single { HealthStatusRepository(get(), get()) }
    single { CategoryRepository(get(), get(), get()) }
    single { DishPreferredRepository(get()) }
}
