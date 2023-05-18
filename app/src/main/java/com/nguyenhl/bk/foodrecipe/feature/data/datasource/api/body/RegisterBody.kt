package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body

import com.google.gson.annotations.SerializedName

data class RegisterBody(
    @SerializedName("email")
    var email: String,
    @SerializedName("password")
    var password: String,
    @SerializedName("confirmedPassword")
    var confirmedPassword: String,
)
