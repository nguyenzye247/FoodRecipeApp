package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.searchfilter

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.ApiIngredient

data class IngredientFilterResponse(
    @SerializedName("ingredients")
    val ingredients: List<ApiIngredient>
)
