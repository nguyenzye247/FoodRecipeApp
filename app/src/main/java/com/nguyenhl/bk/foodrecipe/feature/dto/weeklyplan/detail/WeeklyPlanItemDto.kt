package com.nguyenhl.bk.foodrecipe.feature.dto.weeklyplan.detail

data class WeeklyPlanItemDto(
    val weekDay: String,
    val meal: WeeklyPlanMealDto
)

data class WeeklyPlanMealDto(
    val mealType: String,
    val recipe: WeeklyPlanRecipeDto
)

data class WeeklyPlanRecipeDto(
    val apiId: String,
    val name: String,
    val idRecipe: String,
    val idRecipeDetail: String,
    val imageUrl: String
)
