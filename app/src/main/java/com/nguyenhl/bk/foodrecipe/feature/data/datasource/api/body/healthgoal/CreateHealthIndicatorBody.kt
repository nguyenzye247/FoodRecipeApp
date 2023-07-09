package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.healthgoal

import com.google.gson.annotations.SerializedName

data class CreateHealthIndicatorBody(
    @SerializedName("weight")
    val weight: Float,
    @SerializedName("blood_sugar")
    val bloodSugar: Int,
    @SerializedName("heart_rate")
    val heartRate: Int,
    @SerializedName("create_at")
    val createdAt: String
)
