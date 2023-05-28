package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.recipe

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.recipe.ApiRecipe

data class SearchRecipeResponse(
    @SerializedName("recipes")
    val recipes: List<ApiRecipe>,
    @SerializedName("pages")
    val pageCount: Int
)
