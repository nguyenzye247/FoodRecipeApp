package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.userinfo.UserInfo

data class UserInfoResponse(
    @SerializedName("info")
    val info: UserInfo?,
    @SerializedName("status")
    val status: Boolean
)
