package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao.UserDao
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.User

@Database(
    entities = [User::class],
    version = 1,
    exportSchema = true
)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
