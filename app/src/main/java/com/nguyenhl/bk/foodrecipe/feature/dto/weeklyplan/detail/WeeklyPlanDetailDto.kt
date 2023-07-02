package com.nguyenhl.bk.foodrecipe.feature.dto.weeklyplan.detail

data class WeeklyPlanDetailDto(
    val apiId: String,
    val idWeeklyPlanDetail: String,
    val weeklyPlans: List<WeeklyPlanItemDto>
)
