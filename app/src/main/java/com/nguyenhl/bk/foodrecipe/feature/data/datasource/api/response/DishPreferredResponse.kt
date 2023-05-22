package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.userinfo.ApiDishPreferred

data class DishPreferredResponse(
    @SerializedName("data")
    val data: List<ApiDishPreferred>
)
