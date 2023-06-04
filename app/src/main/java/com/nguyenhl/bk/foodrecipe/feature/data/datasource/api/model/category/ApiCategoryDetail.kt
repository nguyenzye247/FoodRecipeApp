package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.category

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.CategoryDetail
import com.nguyenhl.bk.foodrecipe.feature.dto.CategoryDetailDto

data class ApiCategoryDetail(
    @SerializedName("_id")
    val id: String,
    @SerializedName("id_category_detail")
    val idCategoryDetail: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("url_image")
    val imageUrl: String,
    @Transient
    var idCategory: String = "",
)

internal fun ApiCategoryDetail.toCategoryDetail(): CategoryDetail {
    return CategoryDetail(
        idApiCategory = this.idCategory,
        idApi = this.id,
        idCategoryDetail = this.idCategoryDetail,
        name = this.name,
        imageUrl = this.imageUrl
    )
}

internal fun ApiCategoryDetail.toCategoryDetailDto(): CategoryDetailDto {
    return CategoryDetailDto(
        idCategoryDetail = this.idCategoryDetail,
        name = this.name,
        imageUrl = this.imageUrl
    )
}
