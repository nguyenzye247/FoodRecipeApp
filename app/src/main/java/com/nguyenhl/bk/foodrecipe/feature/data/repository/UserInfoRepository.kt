package com.nguyenhl.bk.foodrecipe.feature.data.repository

import androidx.annotation.WorkerThread
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.userinfo.UserInfoPostBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.userinfo.UserInfoPutBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.BaseApiErrorModelMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.CreateUserInfoErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.GetUserInfoErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.UpdateUserInfoErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.UserInfoService
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserInfoRepository constructor(
    private val userInfoService: UserInfoService
) : Repository {

    @WorkerThread
    fun getApiUserInfo(token: String) = flow {
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
    fun createApiUserInfo(userInfo: UserInfoPostBody) = flow {
        userInfoService.createUserInfo(userInfo)
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
    fun updateApiUserInfo(userInfo: UserInfoPutBody) = flow {
        userInfoService.updateUserInfo(userInfo)
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
}
