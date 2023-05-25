package com.nguyenhl.bk.foodrecipe

import android.app.Application
import android.view.Gravity
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.GlobalResponseOperator
import com.nguyenhl.bk.foodrecipe.feature.di.apiModule
import com.nguyenhl.bk.foodrecipe.feature.di.databaseModule
import com.nguyenhl.bk.foodrecipe.feature.di.repositoryModule
import com.nguyenhl.bk.foodrecipe.feature.di.viewModelModule
import com.skydoves.sandwich.SandwichInitializer
import es.dmoral.toasty.Toasty
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

        //Init toast
        Toasty.Config.getInstance()
            .tintIcon(true) // optional (apply textColor also to the icon)
//            .setToastTypeface(@NonNull Typeface typeface) // optional
            .setTextSize(14) // optional
            .allowQueue(true) // optional (prevents several Toastys from queuing)
            .setGravity(Gravity.BOTTOM) // optional (set toast gravity, offsets are optional)
//            .supportDarkTheme(boolean supportDarkTheme) // optional (whether to support dark theme or not)
            .setRTL(true) // optional (icon is on the right)
            .apply() // required
    }
}
