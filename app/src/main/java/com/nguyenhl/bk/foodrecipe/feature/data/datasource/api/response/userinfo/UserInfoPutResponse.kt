package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.userinfo

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.dto.ApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.dto.CommonResponseData

data class UserInfoPutResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)

internal fun UserInfoPutResponse.toApiCommonResponse(): ApiCommonResponse =
    ApiCommonResponse(
        CommonResponseData(message),
        status
    )
