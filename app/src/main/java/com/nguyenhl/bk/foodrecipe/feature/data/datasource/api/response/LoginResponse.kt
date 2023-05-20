package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.ApiAuthData
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.ApiAuthModel

data class LoginResponse(
    @SerializedName("token")
    val token: String,
    @SerializedName("status")
    val status: Boolean
)

internal fun LoginResponse.toAuthenticationModel(): ApiAuthModel =
    ApiAuthModel(
        ApiAuthData(token),
        status
    )
