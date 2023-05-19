package com.nguyenhl.bk.foodrecipe

import android.app.Application
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.GlobalResponseOperator
import com.nguyenhl.bk.foodrecipe.feature.di.apiModule
import com.nguyenhl.bk.foodrecipe.feature.di.databaseModule
import com.nguyenhl.bk.foodrecipe.feature.di.repositoryModule
import com.nguyenhl.bk.foodrecipe.feature.di.viewModelModule
import com.skydoves.sandwich.SandwichInitializer
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        //TODO: init stuffs
        startKoin {
            androidContext(this@App)
            modules(apiModule)
            modules(databaseModule)
            modules(repositoryModule)
            modules(viewModelModule)
        }

        // initialize global sandwich operator
        SandwichInitializer.sandwichOperators += GlobalResponseOperator<Any>(this)
    }
}
