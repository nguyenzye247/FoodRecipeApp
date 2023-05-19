package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model

data class AuthenticationModel(
    val data: AuthenticationData,
    val status: Boolean
)

data class AuthenticationData(
    val value: String,
)
