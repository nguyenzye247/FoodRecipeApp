package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.auth.RegisterBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.auth.RegisterResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterRetrofitService {
    companion object {
        const val REGISTER_POST_EP = "user/register"
    }

    @POST(REGISTER_POST_EP)
    suspend fun registerNewAccount(
        @Body registerBody: RegisterBody
    ): ApiResponse<RegisterResponse>
}
