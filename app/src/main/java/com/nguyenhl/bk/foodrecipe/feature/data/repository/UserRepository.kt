package com.nguyenhl.bk.foodrecipe.feature.data.repository

import androidx.annotation.WorkerThread
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao.UserDao
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.User

class UserRepository(
    private val userDao: UserDao
): Repository {

    @WorkerThread
    suspend fun saveUser(user: User) {
        val userExisted = userDao.getUserByUserId(user.userId) != null
        if (userExisted) {
            userDao.updateUser(user)
        } else {
            userDao.insertUser(user)
        }
    }
}
