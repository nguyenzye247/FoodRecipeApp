package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.userinfo.UserInfoPostBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.userinfo.UserInfoPutBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.userinfo.UserInfoGetResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.userinfo.UserInfoPostResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.userinfo.UserInfoPutResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface UserInfoService {
    companion object {
        const val USER_INFO_GET_EP = "info"
        const val USER_INFO_PUT_EP = "info/update"
        const val USER_INFO_POST_EP = "info/create"
    }

    @POST(USER_INFO_GET_EP)
    suspend fun getUserInfo(
        @Header("Authorization") token: String
    ): ApiResponse<UserInfoGetResponse>

    @POST(USER_INFO_PUT_EP)
    suspend fun updateUserInfo(
        @Header("Authorization") token: String,
        @Body userInfoBody: UserInfoPutBody
    ): ApiResponse<UserInfoPutResponse>

    @POST(USER_INFO_POST_EP)
    suspend fun createUserInfo(
        @Header("Authorization") token: String,
        @Body userInfoBody: UserInfoPostBody
    ): ApiResponse<UserInfoPostResponse>
}
