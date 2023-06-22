package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.calendar

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.recipe.ApiRecipe
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.recipe.toRecipeDto
import com.nguyenhl.bk.foodrecipe.feature.dto.calendar.RecipeByDateDto
import com.nguyenhl.bk.foodrecipe.feature.dto.enumdata.MealType

data class ApiRecipeByDate(
    @SerializedName("_id")
    val id: String,
    @SerializedName("type")
    val mealType: String,
    @SerializedName("recipe")
    val recipe: ApiRecipe
)

internal fun ApiRecipeByDate.toRecipeByDateDto(): RecipeByDateDto {
    return RecipeByDateDto(
        idApi = this.id,
        mealType = MealType.getMealTypeFrom(this.mealType),
        recipe = this.recipe.toRecipeDto()
    )
}
