package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api

import android.app.Application
import com.nguyenhl.bk.foodrecipe.BuildConfig
import com.nguyenhl.bk.foodrecipe.core.extension.longToast
import com.nguyenhl.bk.foodrecipe.core.extension.toast
import com.nguyenhl.bk.foodrecipe.core.extension.toastError
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.RegisterResponseMapper
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.StatusCode
import com.skydoves.sandwich.map
import com.skydoves.sandwich.message
import com.skydoves.sandwich.operators.ApiResponseSuspendOperator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class GlobalResponseOperator<T> constructor(
    private val application: Application
): ApiResponseSuspendOperator<T>(){

    override suspend fun onSuccess(apiResponse: ApiResponse.Success<T>) = Unit

    override suspend fun onError(apiResponse: ApiResponse.Failure.Error<T>) {
        withContext(Dispatchers.Main) {
            apiResponse.run {
                Timber.d(message())

                when(statusCode) {
                    StatusCode.InternalServerError -> {
                        toast("InternalServerError")
                        Timber.w("InternalServerError")
                    }
                    StatusCode.BadGateway -> {
                        toast("BadGateway")
                        Timber.w("BadGateway")
                    }
                    StatusCode.Unauthorized -> {
                        toast("Unauthorized")
                        Timber.w("Unauthorized")
                    }
                    else -> {}
                }

                map(RegisterResponseMapper) {
                    Timber.w(message)
                }
            }
        }
    }

//    override suspend fun onException(apiResponse: ApiResponse.Failure.Exception<T>) {
//        withContext(Dispatchers.Main) {
//            apiResponse.run {
//                Timber.d(message)
//                toast("Exception!")
//            }
//        }
//    }

    override suspend fun onException(apiResponse: ApiResponse.Failure.Exception<T>) = Unit

    private fun toast(message: String) {
        if (BuildConfig.DEBUG) {
            application.toastError(message)
        }
    }
}
