package com.nguyenhl.bk.foodrecipe.feature.data.repository

import androidx.annotation.WorkerThread
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.LoginBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.LoginErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.LoginRetrofitService
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginRepository constructor(
    private val loginService: LoginRetrofitService
): Repository {

    @WorkerThread
    fun loginToAccount(
        email: String,
        password: String
    ) = flow {
        val loginBody = LoginBody(email, password)
        loginService.loginToAccount(loginBody)
            .suspendOnSuccess {
                emit(data)
            }
            .suspendOnError(LoginErrorResponseMapper) {
                emit(this)
            }

    }.flowOn(Dispatchers.IO)
}
