package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.healthgoal.indicator

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.dto.healthgoal.HealthIndicatorDto

data class ApiHealthIndicator(
    @SerializedName("_id")
    val id: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("value")
    val value: Float,
    @SerializedName("unit")
    val unit: String,
    @SerializedName("type")
    val type: String
)

internal fun ApiHealthIndicator.toHealthIndicatorDto(): HealthIndicatorDto {
    return HealthIndicatorDto(
        idApi = this.id,
        createdAt = this.createdAt,
        value = this.value,
        unit = this.unit,
        type = this.type
    )
}
