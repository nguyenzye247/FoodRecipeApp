package com.nguyenhl.bk.foodrecipe.feature.dto.healthgoal

import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto

data class MealSuggestDto(
    val idApi: String,
    val calories: Float,
    val recipe: MealSuggestRecipeDto,
    val type: String
)

data class MealSuggestRecipeDto(
    val idApi: String,
    val idRecipe: String,
    val idRecipeDetail: String,
    val name: String,
    val imageUrl: String
)

internal fun MealSuggestRecipeDto.toRecipeDto(): RecipeDto {
    return RecipeDto(
        apiId = this.idApi,
        idRecipe = this.idRecipe,
        idRecipeDetail = this.idRecipeDetail,
        name = this.name,
        imageUrl = this.imageUrl,
        totalTime = 0,
        author = "",
        categoryDetails = emptyList(),
        isLiked = false
    )
}
