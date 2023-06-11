package com.nguyenhl.bk.foodrecipe.feature.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class IngredientDto(
    val idIngredient: String,
    val name: String,
    val imageUrl: String,
    val idIngredientDetail: String
): Parcelable
