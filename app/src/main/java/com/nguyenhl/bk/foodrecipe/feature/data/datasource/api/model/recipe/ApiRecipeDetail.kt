package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.recipe

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDetailDto

data class ApiRecipeDetail(
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("prep_time")
    val prepTime: Int?,
    @SerializedName("cooke_time")
    val cookTime: Int?,
    @SerializedName("serves")
    val serveCount: Int,
    @SerializedName("author")
    val author: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("ingredients_detail")
    val ingredientDetails: List<String>,
    @SerializedName("methods")
    val methods: List<String>
)

internal fun ApiRecipeDetail.toRecipeDetailDto(): RecipeDetailDto {
    return RecipeDetailDto(
        idApi = this.id,
        name = this.name,
        imageUrl = this.imageUrl,
        prepTime = this.prepTime,
        cookTime = this.cookTime,
        serveCount = this.serveCount,
        author = this.author,
        description = this.description,
        ingredientDetails = this.ingredientDetails,
        methods = this.methods
    )
}
