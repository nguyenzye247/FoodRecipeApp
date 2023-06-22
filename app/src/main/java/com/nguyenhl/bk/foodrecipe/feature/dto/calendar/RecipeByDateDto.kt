package com.nguyenhl.bk.foodrecipe.feature.dto.calendar

import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto
import com.nguyenhl.bk.foodrecipe.feature.dto.enumdata.MealType

data class RecipeByDateDto(
    val idApi: String,
    val mealType: MealType?,
    val recipe: RecipeDto
)
