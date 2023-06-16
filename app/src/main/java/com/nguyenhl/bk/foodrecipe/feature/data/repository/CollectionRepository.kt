package com.nguyenhl.bk.foodrecipe.feature.data.repository

import androidx.annotation.WorkerThread
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.GlobalRetryPolicy
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.GetCollectionErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.CollectionService
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao.CollectionDao
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.Collection
import com.skydoves.sandwich.retry.runAndRetry
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CollectionRepository constructor(
    private val collectionService: CollectionService,
    private val collectionDao: CollectionDao
): Repository {

    @WorkerThread
    fun fetchAllCollections() = flow {
        runAndRetry(GlobalRetryPolicy()) { _, _ ->
            collectionService.getAllCollections()
                .suspendOnSuccess {
                    emit(data)
                }
                .suspendOnError(GetCollectionErrorResponseMapper) {
                    emit(this)
                }
                .suspendOnException {
                    emit(null)
                }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun saveCollections(collections: List<Collection>) {
        collectionDao.insertAll(collections)
    }
}
