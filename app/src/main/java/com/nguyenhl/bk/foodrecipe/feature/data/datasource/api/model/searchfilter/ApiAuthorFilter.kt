package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.searchfilter

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.dto.searchfilter.SearchFilterItemDto

data class ApiAuthorFilter(
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url_image")
    val imageUrl: String
)

internal fun ApiAuthorFilter.toSearchFilterDto(): SearchFilterItemDto {
    return SearchFilterItemDto(
        idApi = this.id,
        idDetail = "",
        name = this.name,
        value = 0
    )
}
