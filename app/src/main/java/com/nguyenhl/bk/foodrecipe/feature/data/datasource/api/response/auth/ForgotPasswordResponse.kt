package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.auth

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.dto.ApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.dto.CommonResponseData

data class ForgotPasswordResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)

internal fun ForgotPasswordResponse.toApiCommonResponse(): ApiCommonResponse {
    return ApiCommonResponse(
        CommonResponseData(this.message),
        this.status
    )
}
