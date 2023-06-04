package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.ApiIngredient

data class IngredientResponse(
    @SerializedName("ingredients")
    val ingredients: List<ApiIngredient>,
    @SerializedName("pages")
    val pageCount: Int
)
