package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.dto.CommonResponseData
import com.nguyenhl.bk.foodrecipe.feature.dto.ApiCommonResponse

data class RegisterResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)

internal fun RegisterResponse.toApiCommonResponse(): ApiCommonResponse =
    ApiCommonResponse(
        CommonResponseData(message),
        status
    )
