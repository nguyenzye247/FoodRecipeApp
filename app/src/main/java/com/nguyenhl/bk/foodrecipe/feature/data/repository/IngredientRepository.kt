package com.nguyenhl.bk.foodrecipe.feature.data.repository

import androidx.annotation.WorkerThread
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nguyenhl.bk.foodrecipe.core.common.INITIAL_LOAD_SIZE
import com.nguyenhl.bk.foodrecipe.core.common.PAGE_SIZE
import com.nguyenhl.bk.foodrecipe.core.common.PREFETCH_DIST
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.GlobalRetryPolicy
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.GetIngredientDetailErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.GetIngredientErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.pagingsource.IngredientEP
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.pagingsource.IngredientPagingSource
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.IngredientService
import com.nguyenhl.bk.foodrecipe.feature.dto.IngredientDto
import com.skydoves.sandwich.retry.runAndRetry
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class IngredientRepository constructor(
    private val ingredientService: IngredientService
) : Repository {

    @WorkerThread
    fun fetchIngredients(): Flow<PagingData<IngredientDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = INITIAL_LOAD_SIZE,
                prefetchDistance = PREFETCH_DIST
            ),
            pagingSourceFactory = {
                IngredientPagingSource(IngredientEP.ALL, ingredientService)
            }
        ).flow
            .flowOn(Dispatchers.IO)
    }

    @WorkerThread
    fun searchIngredients(searchString: String): Flow<PagingData<IngredientDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = INITIAL_LOAD_SIZE,
                prefetchDistance = PREFETCH_DIST
            ),
            pagingSourceFactory = {
                IngredientPagingSource(
                    IngredientEP.SEARCH,
                    ingredientService,
                    searchString = searchString
                )
            }
        ).flow
            .flowOn(Dispatchers.IO)
    }

    @WorkerThread
    fun fetchIngredientsByAlphabet(alphabetKey: Char): Flow<PagingData<IngredientDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = INITIAL_LOAD_SIZE,
                prefetchDistance = PREFETCH_DIST
            ),
            pagingSourceFactory = {
                IngredientPagingSource(
                    IngredientEP.ALPHABET,
                    ingredientService,
                    alphabetKey = alphabetKey
                )
            }
        ).flow
            .flowOn(Dispatchers.IO)
    }

    @WorkerThread
    fun fetchTop10Ingredients() = flow {
        runAndRetry(GlobalRetryPolicy()) { _, _ ->
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
        }
    }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun fetchIngredientDetail(detailId: String) = flow {
        runAndRetry(GlobalRetryPolicy()) { _, _ ->
            ingredientService.getIngredientDetail(detailId)
                .suspendOnSuccess {
                    emit(data)
                }
                .suspendOnError(GetIngredientDetailErrorResponseMapper) {
                    emit(this)
                }
                .suspendOnException {
                    Timber.tag("OKEOKE").w(this.message)
                    emit(null)
                }
        }
    }.flowOn(Dispatchers.IO)
}
