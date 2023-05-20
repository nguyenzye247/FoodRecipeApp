package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.HealthStatusResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface HealthStatusService {
    companion object {
        const val HEALTH_GET_EP = "healthcare"
    }

    @GET(HEALTH_GET_EP)
    suspend fun getAllHealthStatuses(): ApiResponse<HealthStatusResponse>
}
