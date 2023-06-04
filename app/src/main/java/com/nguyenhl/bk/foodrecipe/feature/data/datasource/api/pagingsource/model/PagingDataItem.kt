package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.pagingsource.model

data class PagingDataItem(
    val idApi: String,
    val idObject: String,
    val name: String,
    val imageUrl: String,
    val description: String = ""
)
