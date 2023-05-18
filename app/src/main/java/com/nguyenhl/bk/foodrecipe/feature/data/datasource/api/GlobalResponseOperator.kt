package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api

import android.app.Application
import android.util.Log
import com.nguyenhl.bk.foodrecipe.core.extension.toast
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.ErrorResponseMapper
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.StatusCode
import com.skydoves.sandwich.map
import com.skydoves.sandwich.message
import com.skydoves.sandwich.operators.ApiResponseSuspendOperator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GlobalResponseOperator<T> constructor(
    private val application: Application
): ApiResponseSuspendOperator<T>(){

    override suspend fun onSuccess(apiResponse: ApiResponse.Success<T>) = Unit

    override suspend fun onError(apiResponse: ApiResponse.Failure.Error<T>) {
        withContext(Dispatchers.Main) {
            apiResponse.run {
                Log.d("ApiResponse.Failure.Error", message())

                when(statusCode) {
                    StatusCode.InternalServerError -> toast("InternalServerError")
                    StatusCode.BadGateway -> toast("BadGateway")
                    else -> toast("$statusCode(${statusCode.code}): ${message()}")
                }

                map(ErrorResponseMapper) {
                    Log.d("RegisterErrorResponse", message())
                }
            }
        }
    }

    override suspend fun onException(apiResponse: ApiResponse.Failure.Exception<T>) {
        withContext(Dispatchers.Main) {
            apiResponse.run {
                Log.d("ApiResponse.Failure.Exception", message())
                toast(message())
            }
        }
    }

    private fun toast(message: String) {
        application.toast(message)
    }
}
