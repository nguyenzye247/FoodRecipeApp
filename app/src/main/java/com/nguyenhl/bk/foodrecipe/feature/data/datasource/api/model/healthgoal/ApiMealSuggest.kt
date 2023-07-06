package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.healthgoal

import com.google.gson.annotations.SerializedName

data class ApiMealSuggest(
    @SerializedName("_id")
    val id: String,
    @SerializedName("calories")
    val calories: Float,
    @SerializedName("recipe")
    val recipe: ApiMealSuggestRecipe,
    @SerializedName("type")
    val type: String
)

data class ApiMealSuggestRecipe(
    @SerializedName("_id")
    val id: String,
    @SerializedName("id_recipe")
    val idRecipe: String,
    @SerializedName("id_recipe_detail")
    val idRecipeDetail: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("image_url")
    val imageUrl: String
)
