package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.auth.ForgotPasswordBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.auth.ForgotPasswordResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ForgotPasswordService {
    companion object {
        const val FORGOT_PASSWORD_POST_EP = "user/forgot"
    }

    @POST(FORGOT_PASSWORD_POST_EP)
    suspend fun sendForgotPassword(
        @Body forgotPasswordBody: ForgotPasswordBody
    ): ApiResponse<ForgotPasswordResponse>
}
