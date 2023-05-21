package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.userinfo

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.dto.ApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.dto.CommonResponseData

data class UserInfoPostResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)

internal fun UserInfoPostResponse.toApiCommonResponse(): ApiCommonResponse {
    return ApiCommonResponse(
        CommonResponseData(this.message),
        this.status
    )
}
