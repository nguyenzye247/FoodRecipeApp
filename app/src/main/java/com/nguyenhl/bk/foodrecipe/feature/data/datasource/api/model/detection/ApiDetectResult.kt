package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.detection

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.core.extension.roundFloatToTwoDecimalPlaces
import com.nguyenhl.bk.foodrecipe.feature.dto.detect.DetectResultDto

data class ApiDetectResult(
    @SerializedName("x")
    val xStart: Float,
    @SerializedName("y")
    val yStart: Float,
    @SerializedName("width")
    val width: Float,
    @SerializedName("height")
    val height: Float,
    @SerializedName("confidence")
    val confidence: Float,
    @SerializedName("class")
    val className: String
)

internal fun ApiDetectResult.toDetectResultDto(): DetectResultDto {
    return DetectResultDto(
        xStart = this.xStart,
        yStart = this.yStart,
        width = this.width,
        height = this.height,
        confidence = this.confidence.roundFloatToTwoDecimalPlaces(),
        classResultName = this.className
    )
}
