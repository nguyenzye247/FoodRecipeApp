package com.nguyenhl.bk.foodrecipe.feature.data.repository

import androidx.annotation.WorkerThread
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.GetIngredientErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.IngredientService
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class IngredientRepository constructor(
    private val ingredientService: IngredientService
): Repository {

    @WorkerThread
    fun fetchIngredients() = flow {
        ingredientService.getIngredients()
            .suspendOnSuccess {
                emit(data)
            }
            .suspendOnError(GetIngredientErrorResponseMapper) {
                emit(this)
            }
            .suspendOnException {
                emit(null)
            }
    }.flowOn(Dispatchers.IO)
}
