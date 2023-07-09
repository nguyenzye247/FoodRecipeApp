package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.healthgoal.indicator

import com.google.gson.annotations.SerializedName

data class ApiHealthIndicator(
    @SerializedName("_id")
    val id: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("value")
    val value: Float,
    @SerializedName("unit")
    val unit: String,
    @SerializedName("type")
    val type: String
)
