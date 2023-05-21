package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.HealthStatus

data class  ApiHealthStatus(
    @SerializedName("_id")
    val id: String,
    @SerializedName("id_health_care")
    val idHealthStatus: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("key")
    val keys: List<String>
)

internal fun ApiHealthStatus.toHealthStatus(): HealthStatus =
    HealthStatus(
        idApi = this.id,
        idHealthStatus = this.idHealthStatus,
        name = this.name,
    )
