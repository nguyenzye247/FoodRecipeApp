package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

@Parcelize
data class HealthStatusWithCategoryDetail (
    @Embedded
    val healthStatus: HealthStatus,
    @Relation(
        parentColumn = "id_health_status",
        entityColumn = "id_category_detail",
        associateBy = Junction(HealthStatusCategoryDetailCrossRef::class)
    )
    val categoryDetails: List<CategoryDetail>
) : Parcelable
