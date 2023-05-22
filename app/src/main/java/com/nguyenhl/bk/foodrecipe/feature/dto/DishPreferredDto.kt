package com.nguyenhl.bk.foodrecipe.feature.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DishPreferredDto(
    val idApi: String,
    val idDishPreferred: String,
    val name: String,
    val imageUrl: String,
    var isSelected: Boolean = false
) : Parcelable
