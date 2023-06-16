package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.recipe

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.ApiIngredient
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.recipe.ApiRecipeDetail
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.recipe.ApiRecipeNutrient

data class RecipeDetailResponse(
    @SerializedName("ingredients")
    val ingredients: List<ApiIngredient>,
    @SerializedName("nutritions")
    val nutrients: List<ApiRecipeNutrient>,
    @SerializedName("details")
    val recipeDetail: ApiRecipeDetail
)
