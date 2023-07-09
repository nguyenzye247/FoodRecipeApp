package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.interceptor.RequestInterceptor
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.detection.DetectionResponse
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.adapters.ApiResponseCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface DetectionService {
    companion object {
        const val MODEL_API_KEY = "SeH1f4gyjqn1Uc1xhd6O"
        const val MODEL_EP = "food-ingredient-recognition-51ngf/3"

        private val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(RequestInterceptor())
            .callTimeout(7, TimeUnit.SECONDS)
//            .addInterceptor(
            .build()

        private val aiRetrofit: Retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl("https://detect.roboflow.com/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addCallAdapterFactory(ApiResponseCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiDetect = aiRetrofit.create(DetectionService::class.java)
    }

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST(MODEL_EP)
    suspend fun uploadImage(
        @Body encodedFile: String,
        @Query("name") imageName: String,
        @Query("api_key") apiKey: String = MODEL_API_KEY,
    ): ApiResponse<DetectionResponse>
}
