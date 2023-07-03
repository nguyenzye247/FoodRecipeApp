package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.healthgoal

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.healthgoal.ApiPhysicalLevel

data class PhysicalLevelResponse(
    @SerializedName("data")
    val physicalLevels: List<ApiPhysicalLevel>
)
