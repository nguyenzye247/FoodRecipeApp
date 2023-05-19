package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body

import com.google.gson.annotations.SerializedName

data class LoginBody(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)
