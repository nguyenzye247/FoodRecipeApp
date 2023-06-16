package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.recipe

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.dto.NutrientDto

data class ApiRecipeNutrient(
    @SerializedName("_id")
    val id: String,
    @SerializedName("value")
    val value: Float,
    @SerializedName("nutrition_detail")
    val nutrientDetail: ApiNutrientDetail
)

internal fun ApiRecipeNutrient.toNutrientDto(): NutrientDto {
    return NutrientDto(
        idApi = this.id,
        value = this.value,
        nutrientDetail = this.nutrientDetail.toNutrientDetailDto()
    )
}
