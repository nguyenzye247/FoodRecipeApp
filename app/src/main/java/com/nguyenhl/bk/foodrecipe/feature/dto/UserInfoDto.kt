package com.nguyenhl.bk.foodrecipe.feature.dto

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.userinfo.UserInfoPostBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.userinfo.UserInfoPutBody

data class UserInfoDto(
    var name: String,
    var dob: String,
    var gender: Int,
    var height: Float,
    var weight: Float,
    var idHeathStatus: String,
    val idPreferredDishes: List<String> = listOf()
)

internal fun UserInfoDto.toUserInfoPostBody(): UserInfoPostBody {
    return UserInfoPostBody(
        name = this.name,
        dateOfBirth = this.dob,
        gender = this.gender,
        height = this.height,
        weight = this.weight,
        idPreferredDishes = this.idPreferredDishes,
        idHealthStatus = this.idHeathStatus
    )
}

internal fun UserInfoDto.toUserInfoPutBody(): UserInfoPutBody {
    return UserInfoPutBody(
        name = this.name,
        dateOfBirth = this.dob,
        gender = this.gender,
        height = this.height,
        weight = this.weight
    )
}
