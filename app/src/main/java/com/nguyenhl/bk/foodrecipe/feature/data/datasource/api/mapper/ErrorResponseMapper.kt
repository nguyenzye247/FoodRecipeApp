package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper

import com.google.gson.Gson
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.ErrorResponse
import com.skydoves.sandwich.ApiErrorModelMapper
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.StatusCode
import com.skydoves.sandwich.message

abstract class BaseApiErrorModelMapper(
    private val uniqueMessage: String
) : ApiErrorModelMapper<ErrorResponse> {
    override fun map(apiErrorResponse: ApiResponse.Failure.Error<*>): ErrorResponse {
        return try {
            Gson().fromJson(apiErrorResponse.message(), ErrorResponse::class.java)
        } catch (ex: Exception) {
            ErrorResponse(
                uniqueMessage,
                apiErrorResponse.statusCode == StatusCode.OK
            )
        }
    }

}

object ErrorResponseMapper : BaseApiErrorModelMapper("Error: Email is already existed")
object LoginErrorResponseMapper : BaseApiErrorModelMapper("Error: Wrong email or password")
