package com.nguyenhl.bk.foodrecipe.feature.di

import com.nguyenhl.bk.foodrecipe.feature.data.repository.RegisterRepository
import org.koin.dsl.module

val repositoryModule = module {

//    single { MainRepository(get(), get()) }

//    single { DetailRepository(get()) }
    single { RegisterRepository(get()) }
}
