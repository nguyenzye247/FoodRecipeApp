package com.nguyenhl.bk.foodrecipe.feature.data.repository

import androidx.annotation.WorkerThread
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.GlobalRetryPolicy
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.healthgoal.CreateHealthIndicatorBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.healthgoal.GetHealthIndicatorBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.*
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.HealthIndicatorService
import com.skydoves.sandwich.retry.runAndRetry
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class HealthIndicatorRepository constructor(
    private val healthIndicatorService: HealthIndicatorService
) : Repository {

    @WorkerThread
    fun fetchHealthIndicatorByDate(
        token: String,
        healthGoalId: String,
        getHealthIndicatorBody: GetHealthIndicatorBody
    ) = flow {
        runAndRetry(GlobalRetryPolicy()) { _, _ ->
            healthIndicatorService.getHealthIndicatorByDate(
                token,
                healthGoalId,
                getHealthIndicatorBody
            )
                .suspendOnSuccess {
                    emit(data)
                }
                .suspendOnError(GetHealthIndicatorByDateErrorResponseMapper) {
                    emit(this)
                }
                .suspendOnException {
                    emit(null)
                }
        }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun createHealthIndicator(
        token: String,
        healthGoalId: String,
        createHealthIndicatorBody: CreateHealthIndicatorBody
    ) = flow {
        runAndRetry(GlobalRetryPolicy()) { _, _ ->
            healthIndicatorService.createHealthIndicator(
                token,
                healthGoalId,
                createHealthIndicatorBody
            )
                .suspendOnSuccess {
                    emit(data)
                }
                .suspendOnError(CreateHealthIndicatorErrorResponseMapper) {
                    emit(this)
                }
                .suspendOnException {
                    emit(null)
                }
        }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun getWeightIndicator(
        token: String,
        healthGoalId: String
    ) = flow {
        runAndRetry(GlobalRetryPolicy()) { _, _ ->
            healthIndicatorService.getWeightIndicators(
                token,
                healthGoalId
            )
                .suspendOnSuccess {
                    emit(data)
                }
                .suspendOnError(GetWeightIndicatorErrorResponseMapper) {
                    emit(this)
                }
                .suspendOnException {
                    emit(null)
                }
        }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun getBloodSugarIndicator(
        token: String,
        healthGoalId: String
    ) = flow {
        runAndRetry(GlobalRetryPolicy()) { _, _ ->
            healthIndicatorService.getBloodSugarIndicators(
                token,
                healthGoalId
            )
                .suspendOnSuccess {
                    emit(data)
                }
                .suspendOnError(GetBloodSugarIndicatorErrorResponseMapper) {
                    emit(this)
                }
                .suspendOnException {
                    emit(null)
                }
        }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun getHeartRateIndicator(
        token: String,
        healthGoalId: String
    ) = flow {
        runAndRetry(GlobalRetryPolicy()) { _, _ ->
            healthIndicatorService.getHeartRateIndicators(
                token,
                healthGoalId
            )
                .suspendOnSuccess {
                    emit(data)
                }
                .suspendOnError(GetHeartRateIndicatorErrorResponseMapper) {
                    emit(this)
                }
                .suspendOnException {
                    emit(null)
                }
        }
    }.flowOn(Dispatchers.IO)
}
