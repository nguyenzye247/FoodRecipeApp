package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.RegisterErrorResponse
import com.skydoves.sandwich.ApiErrorModelMapper
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.StatusCode
import com.skydoves.sandwich.message

object ErrorResponseMapper : ApiErrorModelMapper<RegisterErrorResponse> {
    override fun map(apiErrorResponse: ApiResponse.Failure.Error<*>): RegisterErrorResponse {
        return RegisterErrorResponse(
            apiErrorResponse.message(),
            apiErrorResponse.statusCode == StatusCode.OK
        )
    }
}
