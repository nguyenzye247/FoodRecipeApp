package com.nguyenhl.bk.foodrecipe.feature.dto

import android.os.Parcelable
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.Recipe
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeDto(
    val apiId: String,
    val idRecipe: String,
    val idRecipeDetail: String,
    val name: String,
    val imageUrl: String,
    val totalTime: Int,
    val author: String,
    val categoryDetails: List<CategoryDetailDto>,
    var isLiked: Boolean,
    var createdAt: Long = 0L
) : Parcelable

internal fun RecipeDto.toRecipe(): Recipe {
    return Recipe(
        idApi = this.apiId,
        idRecipe = this.idRecipe,
        idRecipeDetail = this.idRecipeDetail,
        name = this.name,
        imageUrl = this.imageUrl,
        cookTime = this.totalTime,
        author = this.author,
        isLiked = this.isLiked,
        createdAt = System.currentTimeMillis()
    )
}
