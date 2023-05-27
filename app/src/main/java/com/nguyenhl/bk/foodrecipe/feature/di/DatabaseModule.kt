package com.nguyenhl.bk.foodrecipe.feature.di

import androidx.room.Room
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.RecipeDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            RecipeDatabase::class.java,
            "RecipeApp.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    single { get<RecipeDatabase>().userDao() }
    single { get<RecipeDatabase>().healthStatusDao() }
    single { get<RecipeDatabase>().categoryDao() }
    single { get<RecipeDatabase>().preferredDishDao() }
    single { get<RecipeDatabase>().categoryDetailDao() }
    single { get<RecipeDatabase>().healthStatusCategoryDetailDao() }
    //TODO: add mode dao
}
