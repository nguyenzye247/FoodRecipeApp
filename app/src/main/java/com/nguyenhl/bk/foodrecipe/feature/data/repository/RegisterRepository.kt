package com.nguyenhl.bk.foodrecipe.feature.data.repository

import androidx.annotation.WorkerThread
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.RegisterBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.ErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.RegisterRetrofitService
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RegisterRepository constructor(
    private val registerService: RegisterRetrofitService
) : Repository {

    @WorkerThread
    fun registerNewAccount(
        email: String,
        password: String,
        confirmedPassword: String
    ) = flow {
        val registerBody = RegisterBody(email, password, confirmedPassword)
        registerService.registerNewAccount(registerBody)
            .suspendOnSuccess {
                emit(data)
            }
            .suspendOnError(ErrorResponseMapper) {
                emit(this)
            }
    }.flowOn(Dispatchers.IO)
}