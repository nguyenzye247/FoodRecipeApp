package com.nguyenhl.bk.foodrecipe.feature.data.repository.auth

import androidx.annotation.WorkerThread
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.auth.ForgotPasswordBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.ForgotPasswordErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.ForgotPasswordService
import com.nguyenhl.bk.foodrecipe.feature.data.repository.Repository
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class ForgotPasswordRepository constructor(
    private val forgotPasswordService: ForgotPasswordService
): Repository {

    @WorkerThread
    fun sendForgotPasword(email: String) = flow {
        val forgotPasswordBody = ForgotPasswordBody(email)
        forgotPasswordService.sendForgotPassword(forgotPasswordBody)
            .suspendOnSuccess {
                emit(data)
            }
            .suspendOnError(ForgotPasswordErrorResponseMapper) {
                emit(this)
            }
            .suspendOnException {
                emit(null)
            }
    }.flowOn(Dispatchers.IO)
}
