package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.recipe

import com.google.gson.annotations.SerializedName

data class RecipeByAuthorBody(
    @SerializedName("author")
    val author: String
)
