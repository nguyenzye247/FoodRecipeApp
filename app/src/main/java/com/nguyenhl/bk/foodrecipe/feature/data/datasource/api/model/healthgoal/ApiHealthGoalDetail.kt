package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.healthgoal

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.dto.healthgoal.HealthGoalDetailDto

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

internal fun ApiHealthGoalDetail.toHealthGoalDetailDto(): HealthGoalDetailDto {
    return HealthGoalDetailDto(
        mealSuggests = this.mealSuggests.map { it.toMealSuggestDto() },
        currentWeight = this.currentWeight,
        targetWeight = this.targetWeight,
        dayGoal = this.dayGoal,
        dailyCalories = this.dailyCalories,
        dailyWater = this.dailyWater,
        type = this.type
    )
}
