package com.nguyenhl.bk.foodrecipe.feature.data.repository

import androidx.annotation.WorkerThread
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.calendar.RecipeByDateBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.calendar.RecipeDateBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.AddRecipeToDateErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.GetAllDateHaveRecipeErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.GetRecipeByDateErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.RemoveRecipeFromDateErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.CalendarService
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CalendarRepository constructor(
    private val calendarService: CalendarService
) : Repository {

    @WorkerThread
    fun addRecipeToDate(token: String, addRecipeToDateBody: RecipeDateBody) = flow {
        calendarService.addRecipeToDate(token, addRecipeToDateBody)
            .suspendOnSuccess {
                emit(data)
            }
            .suspendOnError(AddRecipeToDateErrorResponseMapper) {
                emit(this)
            }
            .suspendOnException {
                emit(null)
            }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun removeRecipeFromDate(token: String, recipeByDateId: String) = flow {
        calendarService.removeRecipeFromDate(token, recipeByDateId)
            .suspendOnSuccess {
                emit(data)
            }
            .suspendOnError(RemoveRecipeFromDateErrorResponseMapper) {
                emit(this)
            }
            .suspendOnException {
                emit(null)
            }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun getRecipeByDate(token: String, recipeByDateBody: RecipeByDateBody) = flow {
        calendarService.getRecipeByDate(token, recipeByDateBody)
            .suspendOnSuccess {
                emit(data)
            }
            .suspendOnError(GetRecipeByDateErrorResponseMapper) {
                emit(this)
            }
            .suspendOnException {
                emit(null)
            }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun getAllDateHaveRecipe(token: String) = flow {
        calendarService.getAllDateHaveRecipe(token)
            .suspendOnSuccess {
                emit(data)
            }
            .suspendOnError(GetAllDateHaveRecipeErrorResponseMapper) {
                emit(this)
            }
            .suspendOnException {
                emit(null)
            }
    }.flowOn(Dispatchers.IO)
}
