package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.interceptor

import com.nguyenhl.bk.foodrecipe.feature.di.BEARER_TOKEN
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authorizationHeader = originalRequest.header("Authorization")

        val requestBuilder = originalRequest.newBuilder()
        if (authorizationHeader != null) {
            // Check if the header starts with "Bearer " and add it if it doesn't
            if (!authorizationHeader.startsWith(BEARER_TOKEN)) {
                requestBuilder.header("Authorization", "$BEARER_TOKEN $authorizationHeader")
            }
        }
        requestBuilder.header("Content-Type", "application/json")

        val request = requestBuilder.build()
        Timber.d(request.toString())
        return chain.proceed(request)
    }
}
