package com.nguyenhl.bk.foodrecipe.feature.data.repository

import androidx.annotation.WorkerThread
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.ErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.UserInfoService
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserInfoRepository constructor(
    private val userInfoService: UserInfoService
): Repository {

    @WorkerThread
    fun getUserInfoService(token: String) = flow {
        userInfoService.getUserInfo(token)
            .suspendOnSuccess {
                emit(data)
            }
            .suspendOnError(ErrorResponseMapper) {
                emit(this)
            }
    }.flowOn(Dispatchers.IO)
}
