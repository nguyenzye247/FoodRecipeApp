package com.nguyenhl.bk.foodrecipe.feature.dto.healthgoal

data class HealthGoalDetailDto(
    val mealSuggests: List<MealSuggestDto>,
    val currentWeight: Float,
    val targetWeight: Float,
    val dayGoal: Int,
    val dailyCalories: Float,
    val dailyWater: Float,
    val type: String
)
