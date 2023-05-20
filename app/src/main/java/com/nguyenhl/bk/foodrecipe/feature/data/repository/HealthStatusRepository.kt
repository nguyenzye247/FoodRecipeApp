package com.nguyenhl.bk.foodrecipe.feature.data.repository

import androidx.annotation.WorkerThread
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.HealthStatusErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.HealthStatusService
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class HealthStatusRepository constructor(
    private val healthStatusService: HealthStatusService
) : Repository {

    @WorkerThread
    fun getAllHealthStatus() = flow {
        healthStatusService.getAllHealthStatuses()
            .suspendOnSuccess {
                emit(data)
            }
            .suspendOnError(HealthStatusErrorResponseMapper) {
                emit(this)
            }
    }.flowOn(Dispatchers.IO)
}