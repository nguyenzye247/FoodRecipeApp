package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao.CategoryDao
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao.CategoryDetailDao
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao.HealthStatusDao
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao.UserDao
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.Category
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.CategoryDetail
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.HealthStatus
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.User

@Database(
    entities = [User::class, HealthStatus::class, Category::class, CategoryDetail::class],
    version = 1,
    exportSchema = true
)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun healthStatusDao(): HealthStatusDao
    abstract fun categoryDao(): CategoryDao
    abstract fun categoryDetailDao(): CategoryDetailDao
}
