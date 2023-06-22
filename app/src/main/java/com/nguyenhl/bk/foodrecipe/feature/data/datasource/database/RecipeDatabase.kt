package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao.*
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.*
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.Collection

@Database(
    entities = [
        User::class,
        HealthStatus::class,
        Category::class,
        PreferredDish::class,
        CategoryDetail::class,
        HealthStatusCategoryDetailCrossRef::class,
        Collection::class,
        Recipe::class],
    version = 1,
    exportSchema = true
)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun healthStatusDao(): HealthStatusDao
    abstract fun categoryDao(): CategoryDao
    abstract fun preferredDishDao(): PreferredDishDao
    abstract fun categoryDetailDao(): CategoryDetailDao
    abstract fun healthStatusCategoryDetailDao(): HealthStatusCategoryDetailDao
    abstract fun collectionDao(): CollectionDao
    abstract fun recipeDao(): RecipeDao
}
