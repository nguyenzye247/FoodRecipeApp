package com.nguyenhl.bk.foodrecipe.feature.dto.detect

data class DetectImageResultDto(
    val detectTime: Double,
    val imageInfo: DetectImageInfoDto,
    val results: List<DetectResultDto>
)
