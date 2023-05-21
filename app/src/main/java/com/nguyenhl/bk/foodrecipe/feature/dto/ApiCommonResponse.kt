package com.nguyenhl.bk.foodrecipe.feature.dto

data class ApiCommonResponse(
    val data: CommonResponseData,
    val status: Boolean
)

data class CommonResponseData(
    val value: String,
)
