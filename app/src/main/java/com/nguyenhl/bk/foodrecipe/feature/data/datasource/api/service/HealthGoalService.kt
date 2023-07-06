package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.healthgoal.HealthGoalDetailResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.healthgoal.HealthGoalResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.healthgoal.PhysicalLevelResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface HealthGoalService {
    companion object {
        const val PHYSICAL_LEVEL_GET_EP = "physicalhealthylevel"
        const val HEALTH_GOAL_GET_EP = "healthgoal"
        const val HEALTH_GOAL_DETAIL_GET_EP = "healthgoal/detail"
        const val HEALTH_GOAL_POST_EP = "healthGoal/create"
        const val HEALTH_GOAL_PUT_EP = "healthgoal/update"
    }

    @GET(PHYSICAL_LEVEL_GET_EP)
    suspend fun getAllPhysicalLevel(): ApiResponse<PhysicalLevelResponse>

    @GET(HEALTH_GOAL_GET_EP)
    suspend fun getAllHealthGoal(): ApiResponse<HealthGoalResponse>

    @GET("$HEALTH_GOAL_DETAIL_GET_EP/{healthGoalId}")
    suspend fun getHealthGoalDetail(
        @Path("healthGoalId") healthGoalId: String,
    ): ApiResponse<HealthGoalDetailResponse>

//    @POST(HEALTH_GOAL_POST_EP)
//    suspend fun createHealthGoal(): ApiResponse<CreateHealthGoalResponse>
//
//    @PUT(HEALTH_GOAL_PUT_EP)
//    suspend fun updateHealthGoal(): ApiResponse<>
}
