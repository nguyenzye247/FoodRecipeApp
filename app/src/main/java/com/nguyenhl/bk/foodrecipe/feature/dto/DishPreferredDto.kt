package com.nguyenhl.bk.foodrecipe.feature.dto

data class DishPreferredDto(
    val idApi: String,
    val idDishPreferred: String,
    val name: String,
    val imageUrl: String,
    var isSelected: Boolean = false
)
