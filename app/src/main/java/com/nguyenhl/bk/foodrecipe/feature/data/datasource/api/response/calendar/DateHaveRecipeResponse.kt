package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.calendar

import com.google.gson.annotations.SerializedName

data class DateHaveRecipeResponse(
    @SerializedName("days")
    val days: List<String>
)
