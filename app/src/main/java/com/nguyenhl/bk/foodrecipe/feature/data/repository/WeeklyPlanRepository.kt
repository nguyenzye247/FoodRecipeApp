package com.nguyenhl.bk.foodrecipe.feature.data.repository

import androidx.annotation.WorkerThread
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.GlobalRetryPolicy
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.GetAllWeeklyPlanErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.GetWeeklyPlanDetailErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.WeeklyPlanService
import com.skydoves.sandwich.retry.runAndRetry
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class WeeklyPlanRepository constructor(
    private val weeklyPlanService: WeeklyPlanService
): Repository{

    @WorkerThread
    fun fetchAllWeeklyPlans() = flow {
        runAndRetry(GlobalRetryPolicy()) { _, _ ->
            weeklyPlanService.getAllWeeklyPlan()
                .suspendOnSuccess {
                    emit(data)
                }
                .suspendOnError(GetAllWeeklyPlanErrorResponseMapper) {
                    emit(this)
                }
                .suspendOnException {
                    emit(null)
                }
        }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun fetchWeeklyPlanDetails(idWeeklyPlan: String) = flow {
        runAndRetry(GlobalRetryPolicy()) { _, _ ->
            weeklyPlanService.getWeeklyPlanDetail(idWeeklyPlan)
                .suspendOnSuccess {
                    emit(data)
                }
                .suspendOnError(GetWeeklyPlanDetailErrorResponseMapper) {
                    emit(this)
                }
                .suspendOnException {
                    emit(null)
                }
        }
    }.flowOn(Dispatchers.IO)
}
