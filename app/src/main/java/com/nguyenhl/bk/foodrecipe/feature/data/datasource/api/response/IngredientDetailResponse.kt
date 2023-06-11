package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.ApiIngredientDetail

data class IngredientDetailResponse(
    @SerializedName("details")
    val detail: ApiIngredientDetail
)
