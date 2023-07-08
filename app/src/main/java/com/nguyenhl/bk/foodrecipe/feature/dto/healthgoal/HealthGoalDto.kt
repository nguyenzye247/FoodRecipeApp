package com.nguyenhl.bk.foodrecipe.feature.dto.healthgoal

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class HealthGoalDto(
    val apiId: String,
    val targetWeight: Float,
    val dayGoal: Int,
    val createdAt: String,
    val isFinished: Boolean
) : Parcelable
