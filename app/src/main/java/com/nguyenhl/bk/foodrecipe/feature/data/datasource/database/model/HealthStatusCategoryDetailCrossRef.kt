package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(primaryKeys = ["id_health_status", "id_category_detail"])
data class HealthStatusCategoryDetailCrossRef(
    @ColumnInfo(name = "id_health_status")
    val idHealthStatus: String,
    @ColumnInfo(name = "id_category_detail")
    val idCategoryDetail: String
)
