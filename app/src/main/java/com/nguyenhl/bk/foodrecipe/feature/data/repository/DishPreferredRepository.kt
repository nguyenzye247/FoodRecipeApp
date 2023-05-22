package com.nguyenhl.bk.foodrecipe.feature.data.repository

import androidx.annotation.WorkerThread
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.GetDishPreferredErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.DishPreferredService
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class DishPreferredRepository constructor(
    private val dishPreferredService: DishPreferredService
): Repository {

    @WorkerThread
    fun getAllPreferredDishes() = flow {
        dishPreferredService.getAllPreferredDishes()
            .suspendOnSuccess {
                emit(data)
            }
            .suspendOnError(GetDishPreferredErrorResponseMapper) {
                emit(this)
            }
            .suspendOnException {
                emit(null)
            }
    }.flowOn(Dispatchers.IO)
}
