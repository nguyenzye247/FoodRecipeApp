package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.searchfilter

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.searchfilter.ApiTimeFilter

data class TimeFilterResponse(
    @SerializedName("time")
    val times: List<ApiTimeFilter>
)
