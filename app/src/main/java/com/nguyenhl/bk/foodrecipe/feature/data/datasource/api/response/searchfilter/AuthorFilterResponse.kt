package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.searchfilter

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.searchfilter.ApiAuthorFilter

data class AuthorFilterResponse(
    @SerializedName("authors")
    val authors: List<ApiAuthorFilter>
)
