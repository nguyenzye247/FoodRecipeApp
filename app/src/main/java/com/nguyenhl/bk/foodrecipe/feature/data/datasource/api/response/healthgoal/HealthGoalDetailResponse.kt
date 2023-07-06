package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.healthgoal

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.healthgoal.ApiHealthGoalDetail

data class HealthGoalDetailResponse(
    @SerializedName("data")
    val healthGoalDetail: ApiHealthGoalDetail
)