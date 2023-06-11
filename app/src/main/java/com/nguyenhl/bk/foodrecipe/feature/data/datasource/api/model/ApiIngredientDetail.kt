package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.dto.IngredientDetailDto

data class ApiIngredientDetail(
    @SerializedName("_id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("pronunciation")
    val pronunciation: String,
    @SerializedName("info")
    val info: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("description")
    val description: List<String>
)

internal fun ApiIngredientDetail.toIngredientDetailDto(): IngredientDetailDto {
    return IngredientDetailDto(
        idApi = this.id,
        name = this.name,
        imageUrl = this.imageUrl,
        pronunciation = this.pronunciation,
        info = this.info,
        title = this.title,
        description = this.description
    )
}
