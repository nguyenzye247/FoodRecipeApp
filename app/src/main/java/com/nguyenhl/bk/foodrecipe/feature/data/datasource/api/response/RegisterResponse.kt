package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.AuthenticationData
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.AuthenticationModel

data class RegisterResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)

internal fun RegisterResponse.toAuthenticationModel(): AuthenticationModel =
    AuthenticationModel(
        AuthenticationData(message),
        status
    )
