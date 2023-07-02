package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.weeklyplan

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.dto.weeklyplan.detail.WeeklyPlanItemDto
import com.nguyenhl.bk.foodrecipe.feature.dto.weeklyplan.detail.WeeklyPlanMealDto
import com.nguyenhl.bk.foodrecipe.feature.dto.weeklyplan.detail.WeeklyPlanRecipeDto

data class ApiWeeklyPlanItem(
    @SerializedName("weekday")
    val weekDay: String,
    @SerializedName("meal")
    val meal: ApiWeeklyPlanMeal
)

data class ApiWeeklyPlanMeal(
    @SerializedName("meal_type")
    val mealType: String,
    @SerializedName("recipe")
    val recipe: ApiWeeklyPlanRecipe
)

data class ApiWeeklyPlanRecipe(
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("id_recipe")
    val idRecipe: String,
    @SerializedName("id_recipe_detail")
    val idRecipeDetail: String,
    @SerializedName("image_url")
    val imageUrl: String
)

internal fun ApiWeeklyPlanItem.toWeeklyPlanItemDto(): WeeklyPlanItemDto {
    return WeeklyPlanItemDto(
        weekDay = this.weekDay,
        meal = this.meal.toWeeklyPlanMealDto()
    )
}

internal fun ApiWeeklyPlanMeal.toWeeklyPlanMealDto(): WeeklyPlanMealDto {
    return WeeklyPlanMealDto(
        mealType = this.mealType,
        recipe = this.recipe.toWeeklyPlanRecipeDto()
    )
}

internal fun ApiWeeklyPlanRecipe.toWeeklyPlanRecipeDto(): WeeklyPlanRecipeDto {
    return WeeklyPlanRecipeDto(
        apiId = this.id,
        name = this.name,
        idRecipe = this.idRecipe,
        idRecipeDetail = this.idRecipeDetail,
        imageUrl = this.imageUrl
    )
}
