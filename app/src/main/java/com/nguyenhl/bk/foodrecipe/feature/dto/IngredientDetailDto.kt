package com.nguyenhl.bk.foodrecipe.feature.dto

data class IngredientDetailDto(
    val idApi: String,
    val name: String,
    val imageUrl: String,
    val pronunciation: String,
    val info: String,
    val title: String,
    val description: List<String>
)
