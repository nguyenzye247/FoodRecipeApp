package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao.CategoryDao
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao.CategoryDetailDao
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao.CollectionDao
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao.HealthStatusCategoryDetailDao
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao.HealthStatusDao
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao.PreferredDishDao
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao.UserDao
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.Category
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.CategoryDetail
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.Collection
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.HealthStatus
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.HealthStatusCategoryDetailCrossRef
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.PreferredDish
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.User

@Database(
    entities = [
        User::class,
        HealthStatus::class,
        Category::class,
        PreferredDish::class,
        CategoryDetail::class,
        HealthStatusCategoryDetailCrossRef::class,
        Collection::class],
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
}
