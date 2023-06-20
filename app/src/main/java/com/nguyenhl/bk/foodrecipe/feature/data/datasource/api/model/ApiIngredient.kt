package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.searchfilter.ApiAuthorFilter
import com.nguyenhl.bk.foodrecipe.feature.dto.IngredientDto
import com.nguyenhl.bk.foodrecipe.feature.dto.searchfilter.SearchFilterItemDto

data class ApiIngredient(
    @SerializedName("_id")
    val id: String,
    @SerializedName("id_ingredient")
    val idIngredient: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("id_ingredient_detail")
    val idIngredientDetail: String
)

internal fun ApiIngredient.toIngredientDto(): IngredientDto {
    return IngredientDto(
        idIngredient = this.idIngredient,
        name = this.name,
        imageUrl = this.imageUrl,
        idIngredientDetail = this.idIngredientDetail
    )
}

internal fun ApiIngredient.toSearchFilterDto(): SearchFilterItemDto {
    return SearchFilterItemDto(
        idApi = this.id,
        idDetail = idIngredientDetail,
        name = this.name,
        value = 0
    )
}
