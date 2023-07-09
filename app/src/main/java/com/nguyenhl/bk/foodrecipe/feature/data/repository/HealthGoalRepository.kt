package com.nguyenhl.bk.foodrecipe.feature.data.repository

import androidx.annotation.WorkerThread
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.GlobalRetryPolicy
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.healthgoal.CreateHealthGoalBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.*
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
) : Repository {

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
    fun fetchAllHealthGoals(token: String) = flow {
        runAndRetry(GlobalRetryPolicy()) { _, _ ->
            healthGoalService.getAllHealthGoal(token)
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
    fun fetchHealthGoalDetail(token: String, healthGoalId: String) = flow {
        runAndRetry(GlobalRetryPolicy()) { _, _ ->
            healthGoalService.getHealthGoalDetail(token, healthGoalId)
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

    @WorkerThread
    fun createHealthGoal(token: String, healthGoalBody: CreateHealthGoalBody) = flow {
        healthGoalService.createHealthGoal(token, healthGoalBody)
            .suspendOnSuccess {
                emit(data)
            }
            .suspendOnError(CreateHealthGoalErrorResponseMapper) {
                emit(this)
            }
            .suspendOnException {
                emit(null)
            }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun finishHealthGoal(token: String, healthGoalId: String) = flow {
        healthGoalService.finishHealthGoal(token, healthGoalId)
            .suspendOnSuccess {
                emit(data)
            }
            .suspendOnError(FinishHealthGoalErrorResponseMapper) {
                emit(this)
            }
            .suspendOnException {
                emit(null)
            }
    }.flowOn(Dispatchers.IO)
}
