package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.category

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.Category

data class ApiCategory(
    @SerializedName("_id")
    val id: String,
    @SerializedName("id_category")
    val idCategory: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("category_details")
    val categoryDetails: List<ApiCategoryDetail>
)

internal fun ApiCategory.toCategory(): Category {
    return Category(
        idApi = this.id,
        idCategory = this.idCategory,
        name = this.name
    )
}
