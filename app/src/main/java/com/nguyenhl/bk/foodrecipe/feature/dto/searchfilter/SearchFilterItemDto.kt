package com.nguyenhl.bk.foodrecipe.feature.dto.searchfilter

data class SearchFilterItemDto(
    val idApi: String,
    val idDetail: String,
    val name: String,
    val value: Int,
    var isSelected: Boolean = false
)
