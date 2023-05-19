package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.userinfo

import com.google.gson.annotations.SerializedName

data class HealthStatus(
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("key")
    val keys: List<String>
)
