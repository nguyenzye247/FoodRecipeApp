package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.healthgoal.indicator

import com.google.gson.annotations.SerializedName
import com.nguyenhl.bk.foodrecipe.feature.dto.ApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.dto.CommonResponseData

data class CreateHealthIndicatorResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Boolean
)

internal fun CreateHealthIndicatorResponse.toApiCommonResponse(): ApiCommonResponse {
    return ApiCommonResponse(
        data = CommonResponseData(this.message),
        status = this.status
    )
}
