package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.weeklyplan.WeeklyPlanDetailResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.weeklyplan.WeeklyPlanResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface WeeklyPlanService {
    companion object {
        const val WEEKLY_PLAN_GET_EP = "plan"
        const val WEEKLY_PLAN_DETAIL_GET_EP = "plandetail"
    }

    @GET(WEEKLY_PLAN_GET_EP)
    suspend fun getAllWeeklyPlan(): ApiResponse<WeeklyPlanResponse>

    @GET("$WEEKLY_PLAN_DETAIL_GET_EP/{idPlanDetail}")
    suspend fun getWeeklyPlanDetail(
        @Path("idPlanDetail") idPlanDetail: String
    ): ApiResponse<WeeklyPlanDetailResponse>
}
