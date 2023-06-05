package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.recipe

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.category.ApiCategoryDetail
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.category.toCategoryDetailDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.pagingsource.model.PagingDataItem
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto

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

internal fun ApiRecipe.toRecipeDto(): RecipeDto {
    return RecipeDto(
        apiId = this.id,
        idRecipe = this.idRecipe,
        idRecipeDetail = this.idRecipeDetail,
        name = this.name,
        imageUrl = this.imageUrl,
        totalTime = this.totalTime,
        author = this.author,
        categoryDetails = this.categoryDetails.map { it.toCategoryDetailDto() },
        isLiked = this.isLiked
    )
}
