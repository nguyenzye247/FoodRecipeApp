package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.healthgoal.CreateHealthIndicatorBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.healthgoal.GetHealthIndicatorBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.healthgoal.indicator.CreateHealthIndicatorResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.healthgoal.indicator.GetHealthIndicatorResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface HealthIndicatorService {
    companion object {
        const val HEALTH_INDICATOR_POST_EP = "healthindicator/create"
        const val HEALTH_INDICATOR_BY_DATE_GET_EP = "healthindicator/date"
        const val HEALTH_INDICATOR_WHT_GET_EP = "healthindicator/weight"
        const val HEALTH_INDICATOR_BLS_GET_EP = "healthindicator/bloodsugar"
        const val HEALTH_INDICATOR_HRT_GET_EP = "healthindicator/heartrate"
    }

    @POST("$HEALTH_INDICATOR_POST_EP/{healthGoalId}")
    suspend fun createHealthIndicator(
        @Header("Authorization") token: String,
        @Path("healthGoalId") healthGoalId: String,
        @Body createHealthIndicatorBody: CreateHealthIndicatorBody
    ): ApiResponse<CreateHealthIndicatorResponse>

    // For checking if all fields are added
    @POST("$HEALTH_INDICATOR_BY_DATE_GET_EP/{healthGoalId}")
    suspend fun getHealthIndicatorByDate(
        @Header("Authorization") token: String,
        @Path("healthGoalId") healthGoalId: String,
        @Body getHealthIndicatorBody: GetHealthIndicatorBody
    ): ApiResponse<GetHealthIndicatorResponse>

    @GET("$HEALTH_INDICATOR_WHT_GET_EP/{healthGoalId}")
    suspend fun getWeightIndicators(
        @Header("Authorization") token: String,
        @Path("healthGoalId") healthGoalId: String,
    ): ApiResponse<GetHealthIndicatorResponse>

    @GET("$HEALTH_INDICATOR_BLS_GET_EP/{healthGoalId}")
    suspend fun getBloodSugarIndicators(
        @Header("Authorization") token: String,
        @Path("healthGoalId") healthGoalId: String,
    ): ApiResponse<GetHealthIndicatorResponse>

    @GET("$HEALTH_INDICATOR_HRT_GET_EP/{healthGoalId}")
    suspend fun getHeartRateIndicators(
        @Header("Authorization") token: String,
        @Path("healthGoalId") healthGoalId: String,
    ): ApiResponse<GetHealthIndicatorResponse>
}
