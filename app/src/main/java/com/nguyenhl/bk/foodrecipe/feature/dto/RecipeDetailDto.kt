package com.nguyenhl.bk.foodrecipe.feature.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RecipeDetailDto(
    val idApi: String,
    val name: String,
    val imageUrl: String,
    val prepTime: String?,
    val cookTime: String?,
    val serveCount: Int,
    val author: String,
    val description: String,
    val ingredientDetails: List<String>,
    val methods: List<String>
) : Parcelable
