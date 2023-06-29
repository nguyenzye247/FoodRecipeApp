package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.ingredient

import com.google.gson.annotations.SerializedName

data class IngredientListBody(
    @SerializedName("listID")
    val ingredientIDs: List<String>
)
