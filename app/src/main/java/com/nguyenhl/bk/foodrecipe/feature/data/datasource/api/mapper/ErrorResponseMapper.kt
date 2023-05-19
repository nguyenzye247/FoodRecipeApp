package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper

import com.google.gson.Gson
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.RegisterErrorResponse
import com.skydoves.sandwich.ApiErrorModelMapper
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.StatusCode
import com.skydoves.sandwich.message

object ErrorResponseMapper : ApiErrorModelMapper<RegisterErrorResponse> {

    override fun map(apiErrorResponse: ApiResponse.Failure.Error<*>): RegisterErrorResponse {
        return try {
            Gson().fromJson(apiErrorResponse.message(), RegisterErrorResponse::class.java)
        } catch (ex: Exception) {
            RegisterErrorResponse(
                "Error: Email is already existed",
                apiErrorResponse.statusCode == StatusCode.OK
            )
        }
    }
}
