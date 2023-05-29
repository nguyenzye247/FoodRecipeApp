package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.ApiCollection

data class CollectionResponse(
    @SerializedName("collections")
    val collections: List<ApiCollection>
)
