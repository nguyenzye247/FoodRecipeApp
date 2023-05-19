package com.nguyenhl.bk.foodrecipe.feature.di

import com.nguyenhl.bk.foodrecipe.feature.data.repository.LoginRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.RegisterRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.UserInfoRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { RegisterRepository(get()) }
    single { LoginRepository(get()) }
    single { UserInfoRepository(get()) }
}
