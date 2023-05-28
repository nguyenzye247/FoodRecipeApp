package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.recipe

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.category.ApiCategoryDetail

data class ApiRecipe(
    @SerializedName("_id")
    val id: String,
    @SerializedName("id_recipe")
    val idRecipe: String,
    @SerializedName("id_recipe_detail")
    val idRecipeDetail: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("total_time")
    val totalTime: Int,
    @SerializedName("author")
    val author: String,
    @SerializedName("category_details")
    val categoryDetails: List<ApiCategoryDetail>,
    @SerializedName("like")
    val isLiked: Boolean
)
