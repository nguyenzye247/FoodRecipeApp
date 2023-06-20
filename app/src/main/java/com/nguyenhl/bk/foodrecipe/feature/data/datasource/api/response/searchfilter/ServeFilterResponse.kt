package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.searchfilter

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.searchfilter.ApiServesFilter

data class ServeFilterResponse(
    @SerializedName("serves")
    val serves: List<ApiServesFilter>
)
