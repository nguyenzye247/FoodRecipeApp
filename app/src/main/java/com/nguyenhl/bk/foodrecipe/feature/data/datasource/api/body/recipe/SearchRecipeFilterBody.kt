package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.recipe

import com.google.gson.annotations.SerializedName

data class SearchRecipeFilterBody(
    @SerializedName("id_category_detail")
    var idCategoryDetails: List<String>? = null,
    @SerializedName("total_time")
    var totalTime: Int? = null,
    @SerializedName("id_ingerdient")
    var idIngredients: List<String>?,
    @SerializedName("searchString")
    var queryString: String? = null,
    @SerializedName("author")
    var authorNames: List<String>? = null
)

internal fun SearchRecipeFilterBody.addUserHealthCategoryDetailToSearchBody(
    userCategoryDetails: List<String>
): SearchRecipeFilterBody {
    val newCategoryDetails = this.idCategoryDetails?.let {
        it + userCategoryDetails
    } ?: kotlin.run {
        userCategoryDetails
    }

    return SearchRecipeFilterBody(
        idCategoryDetails = newCategoryDetails,
        totalTime = this.totalTime,
        idIngredients = this.idIngredients,
        queryString = this.queryString,
        authorNames = this.authorNames
    )
}
