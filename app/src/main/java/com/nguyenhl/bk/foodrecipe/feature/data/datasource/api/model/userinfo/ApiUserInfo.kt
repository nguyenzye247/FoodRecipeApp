package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.userinfo

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.User

data class ApiUserInfo(
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
    val preferredDishes: List<ApiDishPreferred>,
    @SerializedName("healthcare")
    val healthStatus: ApiUserHealthStatus
)

internal fun ApiUserInfo.toUser(): User {
    return User(
        userId = this.id,
        name = this.name,
        dateOfBirth = this.dateOfBirth,
        gender = this.gender,
        height = this.height,
        weight = this.weight
    )
}
