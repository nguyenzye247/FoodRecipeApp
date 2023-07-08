package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.healthgoal

import com.google.gson.annotations.SerializedName

data class CreateHealthGoalBody(
    @SerializedName("target_weight")
    val targetWeight: Float,
    @SerializedName("id_physical_healthy_level")
    val idPhysicalLevel: String,
    @SerializedName("day_goal")
    val dayGoal: Int,
    @SerializedName("create_at")
    val createDay: String
)
