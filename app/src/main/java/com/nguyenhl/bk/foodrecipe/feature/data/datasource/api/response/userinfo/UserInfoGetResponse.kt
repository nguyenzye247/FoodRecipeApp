package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.userinfo

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.userinfo.ApiUserInfo

data class UserInfoGetResponse(
    @SerializedName("info")
    val info: ApiUserInfo?,
    @SerializedName("status")
    val status: Boolean
)
