package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.User
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.UserInfo

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user WHERE user_id = :userId")
    suspend fun getUserByUserId(userId: String): User?

    @Query("SELECT * FROM user WHERE user_id = :userId")
    @Transaction
    suspend fun getUserInfoBy(userId: String): UserInfo?

    @Update
    suspend fun updateUser(user: User)

    @Query("UPDATE user " +
            "SET name=:name, date_of_birth=:dob, gender=:gender, height=:height, weight=:weight " +
            "WHERE user_id = :userId")
    suspend fun updateUserById(
        userId: String,
        name: String,
        dob: String,
        gender: Int,
        height: Float,
        weight: Float
    )
}
