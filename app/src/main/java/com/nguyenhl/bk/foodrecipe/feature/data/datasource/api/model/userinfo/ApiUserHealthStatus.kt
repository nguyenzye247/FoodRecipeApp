package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.userinfo

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.HealthStatus

data class ApiUserHealthStatus(
    @SerializedName("_id")
    val id: String,
    @SerializedName("id_health_care")
    val idHealthStatus: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("key")
    val keys: List<String>
)

internal fun ApiUserHealthStatus.toHealthStatus(userId: String): HealthStatus {
    return HealthStatus(
        idApi = this.id,
        idHealthStatus = this.idHealthStatus,
        name = this.name,
        userId = userId
    )
}
