package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.userinfo

import com.google.gson.annotations.SerializedName

data class UserInfoPostBody(
    @SerializedName("name")
    val name: String,
    @SerializedName("dateOfBirth")
    val dateOfBirth: String,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("height")
    val height: Float,
    @SerializedName("weight")
    val weight: Float,
    @SerializedName("id_like_dish")
    val idPreferredDishes: List<String>,
    @SerializedName("id_health_care")
    val idHealthStatus: String
)
