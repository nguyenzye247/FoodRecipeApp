package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.userinfo

import com.google.gson.annotations.SerializedName

data class ApiPreferredDish(
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url_image")
    val imageUrl: String
)
