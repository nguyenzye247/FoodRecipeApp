package com.nguyenhl.bk.foodrecipe.feature.data.repository

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.RegisterBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.RegisterRetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RegisterRepository constructor(
    private val registerService: RegisterRetrofitService
) : Repository {
    fun registerNewAccount(
        email: String,
        password: String,
        confirmedPassword: String
    ) = flow {
        val registerBody = RegisterBody(email, password, confirmedPassword)
        emit(registerService.registerNewAccount(registerBody))
    }.flowOn(Dispatchers.IO)
}