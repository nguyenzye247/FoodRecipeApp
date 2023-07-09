package com.nguyenhl.bk.foodrecipe.feature.dto.healthgoal

data class HealthIndicatorDto(
    val idApi: String,
    val createdAt: String,
    val value: Float,
    val unit: String,
    val type: String
)
