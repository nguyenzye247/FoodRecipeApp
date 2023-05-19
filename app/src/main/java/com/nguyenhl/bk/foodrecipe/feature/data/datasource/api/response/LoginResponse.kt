package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.AuthenticationData
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.AuthenticationModel

data class LoginResponse(
    @SerializedName("token")
    val token: String,
    @SerializedName("status")
    val status: Boolean
)

internal fun LoginResponse.toAuthenticationModel(): AuthenticationModel =
    AuthenticationModel(
        AuthenticationData(token),
        status
    )
