package com.nguyenhl.bk.foodrecipe.feature.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthorDto(
    val idAuthor: String,
    val name: String,
    val imageUrl: String
): Parcelable
