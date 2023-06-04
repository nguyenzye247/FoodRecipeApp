package com.nguyenhl.bk.foodrecipe.feature.data.repository

import androidx.annotation.WorkerThread
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.userinfo.UserInfoPostBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.userinfo.UserInfoPutBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.CreateUserInfoErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.GetUserInfoErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.UpdateUserInfoErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.UserInfoService
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao.UserDao
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.UserInfo
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion

class UserInfoRepository constructor(
    private val userInfoService: UserInfoService,
    private val userDao: UserDao
) : Repository {

    @WorkerThread
    fun fetchApiUserInfo(token: String) = flow {
        userInfoService.getUserInfo(token)
            .suspendOnSuccess {
                emit(data)
            }
            .suspendOnError(GetUserInfoErrorResponseMapper) {
                emit(this)
            }
            .suspendOnException {
                emit(null)
            }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun createApiUserInfo(token: String, userInfo: UserInfoPostBody) = flow {
        userInfoService.createUserInfo(token, userInfo)
            .suspendOnSuccess {
                emit(data)
            }
            .suspendOnError(CreateUserInfoErrorResponseMapper) {
                emit(this)
            }
            .suspendOnException {
                emit(null)
            }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun updateApiUserInfo(token: String, userInfo: UserInfoPutBody) = flow {
        userInfoService.updateUserInfo(token,  userInfo)
            .suspendOnSuccess {
                emit(data)
            }
            .suspendOnError(UpdateUserInfoErrorResponseMapper) {
                emit(this)
            }
            .suspendOnException {
                emit(null)
            }
    }.flowOn(Dispatchers.IO)

    suspend fun getUserInfoByUserId(userId: String): UserInfo? {
        return userDao.getUserInfoBy(userId)
    }
}
