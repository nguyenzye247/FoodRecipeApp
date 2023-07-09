package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.healthgoal

import com.google.gson.annotations.SerializedName

data class GetHealthIndicatorBody(
    @SerializedName("date")
    val date: String
)
