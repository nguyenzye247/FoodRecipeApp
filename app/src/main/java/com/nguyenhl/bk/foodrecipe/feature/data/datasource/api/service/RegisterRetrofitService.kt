package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.RegisterBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.RegisterResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface RegisterRetrofitService {
    companion object {
        const val REGISTER_EP = "user/register"
    }

    @POST(REGISTER_EP)
    suspend fun registerNewAccount(
        @Body registerBody: RegisterBody
    ): ApiResponse<RegisterResponse>
}
