package com.nguyenhl.bk.foodrecipe.feature.data.repository

import androidx.annotation.WorkerThread
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.GlobalRetryPolicy
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.GetAllHealthGoalErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.GetHealthGoalDetailErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.GetPhysicalLevelErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.HealthGoalService
import com.skydoves.sandwich.retry.runAndRetry
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class HealthGoalRepository constructor(
    private val healthGoalService: HealthGoalService
): Repository {

    @WorkerThread
    fun fetchAllPhysicalLevels() = flow {
        runAndRetry(GlobalRetryPolicy()) { _, _ ->
            healthGoalService.getAllPhysicalLevel()
                .suspendOnSuccess {
                    emit(data)
                }
                .suspendOnError(GetPhysicalLevelErrorResponseMapper) {
                    emit(this)
                }
                .suspendOnException {
                    emit(null)
                }
        }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun fetchAllHealthGoals() = flow {
        runAndRetry(GlobalRetryPolicy()) { _, _ ->
            healthGoalService.getAllHealthGoal()
                .suspendOnSuccess {
                    emit(data)
                }
                .suspendOnError(GetAllHealthGoalErrorResponseMapper) {
                    emit(this)
                }
                .suspendOnException {
                    emit(null)
                }
        }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun fetchHealthGoalDetail(healthGoalId: String) = flow {
        runAndRetry(GlobalRetryPolicy()) { _, _ ->
            healthGoalService.getHealthGoalDetail(healthGoalId)
                .suspendOnSuccess {
                    emit(data)
                }
                .suspendOnError(GetHealthGoalDetailErrorResponseMapper) {
                    emit(this)
                }
                .suspendOnException {
                    emit(null)
                }
        }
    }.flowOn(Dispatchers.IO)
}
