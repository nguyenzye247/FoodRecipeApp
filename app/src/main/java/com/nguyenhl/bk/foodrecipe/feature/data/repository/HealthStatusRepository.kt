package com.nguyenhl.bk.foodrecipe.feature.data.repository

import androidx.annotation.WorkerThread
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.HealthStatusErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.HealthStatusService
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao.HealthStatusDao
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.HealthStatus
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class HealthStatusRepository constructor(
    private val healthStatusService: HealthStatusService,
    private val healthStatusDao: HealthStatusDao
) : Repository {

    @WorkerThread
    fun getApiAllHealthStatus() = flow {
        healthStatusService.getAllHealthStatuses()
            .suspendOnSuccess {
                emit(data)
            }
            .suspendOnError(HealthStatusErrorResponseMapper) {
                emit(this)
            }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    suspend fun saveAllHealthStatuses(healthStatuses: List<HealthStatus>) {
        healthStatusDao.insertAll(healthStatuses)
    }

    @WorkerThread
    suspend fun getAllDbHealthStatus(): List<HealthStatus> {
        return healthStatusDao.getAllHealthStatus()
    }
}