package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.searchfilter

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.dto.searchfilter.SearchFilterItemDto

data class ApiServesFilter(
    @SerializedName("_id")
    val id: String,
    @SerializedName("key")
    val key: String,
    @SerializedName("value")
    val value: Int
)

internal fun ApiServesFilter.toSearchFilterDto(): SearchFilterItemDto {
    return SearchFilterItemDto(
        idApi = this.id,
        name = this.key,
        value = this.value
    )
}
