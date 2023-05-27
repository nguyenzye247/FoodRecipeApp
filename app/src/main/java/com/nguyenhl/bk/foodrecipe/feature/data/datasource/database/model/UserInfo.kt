package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserInfo(
    @Embedded
    val user: User,

    @Relation(
        parentColumn = "user_id", entityColumn = "user_id"
    )
    val preferredDishes: List<PreferredDish>,

    @Relation(
        entity = HealthStatus::class,
        parentColumn = "user_id",
        entityColumn = "user_id"
    )
    val healthStatusWithCategoryDetail: HealthStatusWithCategoryDetail

): Parcelable