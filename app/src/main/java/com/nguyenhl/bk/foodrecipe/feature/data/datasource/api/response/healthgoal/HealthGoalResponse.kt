package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.healthgoal

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.healthgoal.ApiHealthGoal

data class HealthGoalResponse(
    @SerializedName("data")
    val healthGoals: List<ApiHealthGoal>
)
