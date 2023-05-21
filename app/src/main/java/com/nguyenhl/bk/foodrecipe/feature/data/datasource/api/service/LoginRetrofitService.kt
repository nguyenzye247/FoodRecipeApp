package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.LoginBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.LoginResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginRetrofitService {
    companion object {
        const val LOGIN_POST_EP = "user/login"
    }

    @POST(LOGIN_POST_EP)
    suspend fun loginToAccount(
        @Body loginBody: LoginBody
    ): ApiResponse<LoginResponse>
}
