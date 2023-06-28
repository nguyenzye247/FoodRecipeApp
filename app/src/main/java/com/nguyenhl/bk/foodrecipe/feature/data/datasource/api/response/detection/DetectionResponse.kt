package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.detection

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.detection.ApiDetectResult
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.detection.toDetectResultDto
import com.nguyenhl.bk.foodrecipe.feature.dto.detect.DetectImageInfoDto
import com.nguyenhl.bk.foodrecipe.feature.dto.detect.DetectImageResultDto
import com.nguyenhl.bk.foodrecipe.feature.dto.detect.DetectResultDto

data class DetectionResponse(
    @SerializedName("time")
    val detectTime: Double,
    @SerializedName("image")
    val imageInfo: ApiDetectImageInfo,
    @SerializedName("predictions")
    val predictions: List<ApiDetectResult>
)

data class ApiDetectImageInfo(
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int
)

internal fun DetectionResponse.toDetectImageResultDto(): DetectImageResultDto {
    return DetectImageResultDto(
        detectTime = this.detectTime,
        imageInfo = DetectImageInfoDto(this.imageInfo.width, this.imageInfo.height),
        results = this.predictions.map { it.toDetectResultDto() }
    )
}
