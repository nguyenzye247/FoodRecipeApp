package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.healthgoal

import com.google.gson.annotations.SerializedName

data class ApiPhysicalLevel(
    @SerializedName("_id")
    val id: String,
    @SerializedName("id_physical_healthy_level")
    val idPhysicalLevel: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("value")
    val value: Float
)
