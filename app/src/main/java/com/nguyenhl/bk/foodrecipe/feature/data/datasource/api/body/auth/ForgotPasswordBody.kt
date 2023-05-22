package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.auth

import com.google.gson.annotations.SerializedName

data class ForgotPasswordBody(
    @SerializedName("email")
    val email: String
)
