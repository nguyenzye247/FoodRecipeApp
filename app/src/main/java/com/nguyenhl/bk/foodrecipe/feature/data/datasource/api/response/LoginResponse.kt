package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.dto.CommonResponseData
import com.nguyenhl.bk.foodrecipe.feature.dto.ApiCommonResponse

data class LoginResponse(
    @SerializedName("token")
    val token: String,
    @SerializedName("status")
    val status: Boolean
)

internal fun LoginResponse.toApiCommonResponse(): ApiCommonResponse =
    ApiCommonResponse(
        CommonResponseData(token),
        status
    )
