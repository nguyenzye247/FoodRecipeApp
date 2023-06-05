package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.pagingsource.model

data class PagingDataItem(
    val apiId: String,
    val idObject: String,
    val name: String,
    val imageUrl: String,
    val description: String?,
    val isLiked: Boolean?,
    val idDetails: String?,
)


