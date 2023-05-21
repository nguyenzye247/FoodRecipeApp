package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.userinfo

import com.google.gson.annotations.SerializedName

data class UserInfoPutResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)
