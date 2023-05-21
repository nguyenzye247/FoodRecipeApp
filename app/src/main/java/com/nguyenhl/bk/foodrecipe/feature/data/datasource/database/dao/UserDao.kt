package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)
}
