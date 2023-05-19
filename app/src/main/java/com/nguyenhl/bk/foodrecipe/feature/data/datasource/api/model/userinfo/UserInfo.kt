package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.userinfo

import com.google.gson.annotations.SerializedName

data class UserInfo(
    @SerializedName("_id")
    val id: String,
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
    @SerializedName("likeDishes")
    val preferredDishes: List<PreferredDish>,
    @SerializedName("healthcare")
    val healthStatus: HealthStatus
)
