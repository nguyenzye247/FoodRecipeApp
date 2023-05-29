package com.nguyenhl.bk.foodrecipe.feature.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CollectionDto(
    val idCollection: String,
    val name: String,
    val imageUrl: String,
    val description: String
): Parcelable
