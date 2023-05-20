package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.ApiHealthStatus

data class HealthStatusResponse(
    @SerializedName("data")
    val data: List<ApiHealthStatus>
)
