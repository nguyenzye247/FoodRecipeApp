package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response

import com.google.gson.annotations.SerializedName

data class RegisterErrorResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)
