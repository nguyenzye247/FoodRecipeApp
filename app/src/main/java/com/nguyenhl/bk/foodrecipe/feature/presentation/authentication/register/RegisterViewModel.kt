package com.nguyenhl.bk.foodrecipe.feature.presentation.authentication.register

import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.RegisterResponse
import com.nguyenhl.bk.foodrecipe.feature.data.repository.RegisterRepository
import com.skydoves.sandwich.ApiResponse
import kotlinx.coroutines.flow.Flow

class RegisterViewModel(
    val input: BaseInput.RegisterInput,
    private val registerRepository: RegisterRepository
) : BaseViewModel(input) {

    fun registerNewAccount(
        email: String,
        password: String,
        confirmedPassword: String
    ): Flow<ApiResponse<RegisterResponse>> {
        return registerRepository.registerNewAccount(email, password, confirmedPassword)
    }
}
