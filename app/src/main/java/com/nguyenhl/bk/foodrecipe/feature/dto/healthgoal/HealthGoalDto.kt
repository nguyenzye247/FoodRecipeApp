package com.nguyenhl.bk.foodrecipe.feature.dto.healthgoal

data class HealthGoalDto(
    val apiId: String,
    val targetWeight: Float,
    val dayGoal: Int,
    val createdAt: String,
    val isFinished: Boolean
)
