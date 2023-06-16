package com.nguyenhl.bk.foodrecipe.feature.dto

import android.os.Parcelable
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
    val isLiked: Boolean
) : Parcelable
