package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.userinfo

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.PreferredDish
import com.nguyenhl.bk.foodrecipe.feature.dto.DishPreferredDto

data class ApiDishPreferred(
    @SerializedName("_id")
    val id: String,
    @SerializedName("id_like_dish")
    val idDishPreferred: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url_image")
    val imageUrl: String
)

internal fun ApiDishPreferred.toDishPreferredDto(): DishPreferredDto {
    return DishPreferredDto(
        idApi = this.id,
        idDishPreferred = this.idDishPreferred,
        name = this.name,
        imageUrl = this.imageUrl
    )
}

internal fun ApiDishPreferred.toPreferredDish(userId: String): PreferredDish {
    return PreferredDish(
        idApi = this.id,
        idDishPreferred = this.idDishPreferred,
        name = this.name,
        imageUrl = this.imageUrl,
        userId = userId
    )
}
