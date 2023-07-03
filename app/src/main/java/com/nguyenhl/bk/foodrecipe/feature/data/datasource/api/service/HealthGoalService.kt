package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.healthgoal.PhysicalLevelResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface HealthGoalService {
    companion object {
        const val PHYSICAL_LEVEL_GET_EP = "physicalhealthylevel"
        const val HEALTH_GOAL_GET_EP = "healthgoal"
        const val HEALTH_GOAL_DETAIL_GET_EP = "healthgoal/detail"
        const val HEALTH_GOALL_POST_EP = "healthGoal/create"
        const val HEALTH_GOALL_PUT_EP = "healthgoal/update"
    }

    @GET(PHYSICAL_LEVEL_GET_EP)
    suspend fun getAllPhysicalLevel(): ApiResponse<PhysicalLevelResponse>


}
