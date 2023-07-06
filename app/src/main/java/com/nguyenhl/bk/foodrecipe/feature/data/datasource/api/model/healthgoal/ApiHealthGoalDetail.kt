package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.healthgoal

import com.google.gson.annotations.SerializedName

data class ApiHealthGoalDetail(
    @SerializedName("meal_suggest")
    val mealSuggests: List<ApiMealSuggest>,
    @SerializedName("current_weight")
    val currentWeight: Float,
    @SerializedName("target_weight")
    val targetWeight: Float,
    @SerializedName("day_goal")
    val dayGoal: Int,
    @SerializedName("daily_calories")
    val dailyCalories: Float,
    @SerializedName("daily_water")
    val dailyWater: Float,
    @SerializedName("type")
    val type: String
)
