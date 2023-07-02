package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.weeklyplan

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.weeklyplan.ApiWeeklyPlan

data class WeeklyPlanResponse(
    @SerializedName("plans")
    val weeklyPlans: List<ApiWeeklyPlan>
)
