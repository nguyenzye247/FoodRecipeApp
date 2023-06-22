package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.calendar

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.calendar.ApiRecipeByDate

data class RecipeByDateResponse(
    @SerializedName("result")
    val data: List<ApiRecipeByDate>
)
