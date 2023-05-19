package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.AuthenticationData
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.AuthenticationModel

data class ErrorResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
) {
    override fun toString(): String {
        return this.message
    }
}

internal fun ErrorResponse.toAuthenticationModel(): AuthenticationModel =
    AuthenticationModel(
        AuthenticationData(message),
        status
    )

