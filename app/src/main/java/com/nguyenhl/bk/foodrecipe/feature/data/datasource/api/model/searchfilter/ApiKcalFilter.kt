package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.searchfilter

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.dto.searchfilter.SearchFilterItemDto

data class ApiKcalFilter(
    @SerializedName("_id")
    val id: String,
    @SerializedName("key")
    val key: String,
    @SerializedName("value")
    val value: Int
)

internal fun ApiKcalFilter.toSearchFilterDto(): SearchFilterItemDto {
    return SearchFilterItemDto(
        idApi = this.id,
        idDetail = "",
        name = this.key,
        value = this.value
    )
}
