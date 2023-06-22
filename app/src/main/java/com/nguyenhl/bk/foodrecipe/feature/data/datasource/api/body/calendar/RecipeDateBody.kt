package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.calendar

import com.google.gson.annotations.SerializedName

data class RecipeDateBody(
    @SerializedName("id_recipe")
    val idRecipe: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("type")
    val mealType: String
)
