package com.nguyenhl.bk.foodrecipe.feature.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryDetailDto(
    val idCategoryDetail: String,
    val name: String,
    val imageUrl: String,
) : Parcelable
