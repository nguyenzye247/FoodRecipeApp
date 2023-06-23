package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.recipe

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.dto.ApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.dto.CommonResponseData

data class LikeRecipeResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)

internal fun LikeRecipeResponse.toApiCommonResponse(): ApiCommonResponse {
    return ApiCommonResponse(
        CommonResponseData(this.message),
        this.status
    )
}
