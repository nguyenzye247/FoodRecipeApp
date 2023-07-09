package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.healthgoal.indicator

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.healthgoal.indicator.ApiHealthIndicator

data class GetHealthIndicatorResponse(
    @SerializedName("data")
    val healthIndicators: List<ApiHealthIndicator>
)
