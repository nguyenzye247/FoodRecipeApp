package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao.*
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.*

@Database(
    entities = [
        User::class,
        HealthStatus::class,
        Category::class,
        CategoryDetail::class,
        HealthStatusCategoryDetailCrossRef::class],
    version = 1,
    exportSchema = true
)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun healthStatusDao(): HealthStatusDao
    abstract fun categoryDao(): CategoryDao
    abstract fun categoryDetailDao(): CategoryDetailDao
    abstract fun healthStatusCategoryDetailDao(): HealthStatusCategoryDetailDao
}
