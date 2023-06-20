package com.nguyenhl.bk.foodrecipe.feature.data.repository.search

import androidx.annotation.WorkerThread
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.GlobalRetryPolicy
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.GetSearchFilterErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.SearchFilterService
import com.nguyenhl.bk.foodrecipe.feature.data.repository.Repository
import com.skydoves.sandwich.retry.runAndRetry
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SearchFilterRepository constructor(
    private val searchFilterService: SearchFilterService
) : Repository {

    @WorkerThread
    fun fetchAllAuthorFilters() = flow {
        runAndRetry(GlobalRetryPolicy()) { _, _ ->
            searchFilterService.getAuthorFilters()
                .suspendOnSuccess {
                    emit(data)
                }
                .suspendOnError(GetSearchFilterErrorResponseMapper) {
                    emit(this)
                }
                .suspendOnException {
                    emit(null)
                }
        }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun fetchIngredientFilters() = flow {
        runAndRetry(GlobalRetryPolicy()) { _, _ ->
            searchFilterService.getIngredientFilters()
                .suspendOnSuccess {
                    emit(data)
                }
                .suspendOnError(GetSearchFilterErrorResponseMapper) {
                    emit(this)
                }
                .suspendOnException {
                    emit(null)
                }
        }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun fetchAllKcalFilters() = flow {
        runAndRetry(GlobalRetryPolicy()) { _, _ ->
            searchFilterService.getKcalFilters()
                .suspendOnSuccess {
                    emit(data)
                }
                .suspendOnError(GetSearchFilterErrorResponseMapper) {
                    emit(this)
                }
                .suspendOnException {
                    emit(null)
                }
        }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun fetchAllServeFilters() = flow {
        runAndRetry(GlobalRetryPolicy()) { _, _ ->
            searchFilterService.getServeFilters()
                .suspendOnSuccess {
                    emit(data)
                }
                .suspendOnError(GetSearchFilterErrorResponseMapper) {
                    emit(this)
                }
                .suspendOnException {
                    emit(null)
                }
        }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun fetchAllTimeFilters() = flow {
        runAndRetry(GlobalRetryPolicy()) { _, _ ->
            searchFilterService.getTimeFilters()
                .suspendOnSuccess {
                    emit(data)
                }
                .suspendOnError(GetSearchFilterErrorResponseMapper) {
                    emit(this)
                }
                .suspendOnException {
                    emit(null)
                }
        }
    }.flowOn(Dispatchers.IO)
}
