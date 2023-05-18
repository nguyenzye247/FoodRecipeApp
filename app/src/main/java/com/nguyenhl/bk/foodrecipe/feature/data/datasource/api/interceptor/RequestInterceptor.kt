package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = originalRequest.newBuilder().url(originalRequest.url).build()
        Log.d("RECIPE_REQUEST", request.toString())
        return chain.proceed(request)
    }
}
