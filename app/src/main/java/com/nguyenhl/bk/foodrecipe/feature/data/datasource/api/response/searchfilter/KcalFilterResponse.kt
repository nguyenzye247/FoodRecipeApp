package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.searchfilter

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.searchfilter.ApiKcalFilter

data class KcalFilterResponse(
    @SerializedName("kcal")
    val kcals: List<ApiKcalFilter>
)
