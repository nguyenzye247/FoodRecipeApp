package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

@Parcelize
data class CategoryWithCategoryDetail(
    @Embedded
    val category: Category,

    @Relation(
        parentColumn = "api_id", entityColumn = "id_category"
    )
    val categoryDetails: List<CategoryDetail>
) : Parcelable
