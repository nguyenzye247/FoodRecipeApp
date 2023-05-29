package com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation
import com.nguyenhl.bk.foodrecipe.feature.dto.UserInfoDto
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

internal fun UserInfo.toUserInfoDto(): UserInfoDto {
    return UserInfoDto(
        name = this.user.name,
        dob = this.user.dateOfBirth,
        gender = this.user.gender,
        height = this.user.height,
        weight = this.user.weight,
        idHeathStatus = this.healthStatusWithCategoryDetail.healthStatus.idHealthStatus,
        idPreferredDishes = this.preferredDishes.map { it.idDishPreferred }
    )
}
