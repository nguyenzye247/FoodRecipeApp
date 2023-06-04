package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.ApiAuthor

data class AuthorResponse(
    @SerializedName("authors")
    val authors: List<ApiAuthor>,
    @SerializedName("pages")
    val pageCount: Int
)
