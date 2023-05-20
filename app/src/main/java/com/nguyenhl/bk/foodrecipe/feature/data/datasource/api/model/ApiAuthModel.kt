package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model

data class ApiAuthModel(
    val data: ApiAuthData,
    val status: Boolean
)

data class ApiAuthData(
    val value: String,
)
