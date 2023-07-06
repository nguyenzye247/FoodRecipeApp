package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.healthgoal

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.dto.healthgoal.HealthGoalDto

data class ApiHealthGoal(
    @SerializedName("_id")
    val id: String,
    @SerializedName("target_weight")
    val targetWeight: Float,
    @SerializedName("day_goal")
    val dayGoal: Int,
    @SerializedName("create_at")
    val createdAt: String,
    @SerializedName("is_finished")
    val isFinished: Boolean
)

internal fun ApiHealthGoal.toHealthGoalDto(): HealthGoalDto {
    return HealthGoalDto(
        apiId = this.id,
        targetWeight = this.targetWeight,
        dayGoal = this.dayGoal,
        createdAt = this.createdAt,
        isFinished = this.isFinished
    )
}
