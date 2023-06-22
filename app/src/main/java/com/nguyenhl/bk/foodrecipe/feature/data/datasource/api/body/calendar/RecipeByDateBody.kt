package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.calendar

import com.google.gson.annotations.SerializedName

data class RecipeByDateBody(
    @SerializedName("date")
    val date: String
)
