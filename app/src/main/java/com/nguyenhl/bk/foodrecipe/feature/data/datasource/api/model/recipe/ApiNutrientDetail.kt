package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.recipe

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.dto.NutrientDetailDto
import com.nguyenhl.bk.foodrecipe.feature.dto.NutrientDto

data class ApiNutrientDetail(
    @SerializedName("_id")
    val id: String,
    @SerializedName("id_nutrition")
    val idNutrient: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("unit")
    val unit: String,
    @SerializedName("url_image")
    val imageUrl: String
)

internal fun ApiNutrientDetail.toNutrientDetailDto(): NutrientDetailDto {
    return NutrientDetailDto(
        idApi = this.id,
        idNutrient = this.idNutrient,
        name = this.name,
        unit = this.unit,
        imageUrl = this.imageUrl
    )
}
