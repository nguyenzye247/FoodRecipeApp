package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.category.ApiCategory

data class CategoryReponse(
    @SerializedName("categories")
    val data: List<ApiCategory>
)