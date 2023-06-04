package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.category.ApiCategory
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.HealthStatus
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.HealthStatusCategoryDetailCrossRef

data class ApiHealthStatus(
    @SerializedName("_id")
    val id: String,
    @SerializedName("id_health_care")
    val idHealthStatus: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("key")
    val keys: List<String>
)

fun ApiHealthStatus.toHealthStatus(): HealthStatus =
    HealthStatus(
        idApi = this.id,
        idHealthStatus = this.idHealthStatus,
        name = this.name,
        userId = ""
    )

fun ApiHealthStatus.toHealthStatusCategoryDetails(): List<HealthStatusCategoryDetailCrossRef> {
    return this.keys.map {
        HealthStatusCategoryDetailCrossRef(
            this.idHealthStatus,
            it
        )
    }
}
