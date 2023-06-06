package com.nguyenhl.bk.foodrecipe.feature.data.repository

import androidx.annotation.WorkerThread
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nguyenhl.bk.foodrecipe.core.common.INITIAL_LOAD_SIZE
import com.nguyenhl.bk.foodrecipe.core.common.PAGE_SIZE
import com.nguyenhl.bk.foodrecipe.core.common.PREFETCH_DIST
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.GetAuthorErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.ApiAuthor
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.recipe.ApiRecipe
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.pagingsource.AuthorPagingSource
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.AuthorService
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AuthorRepository(
    private val authorService: AuthorService
) : Repository {
    @WorkerThread
    fun fetchAuthors(): Flow<PagingData<ApiAuthor>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = INITIAL_LOAD_SIZE,
                prefetchDistance = PREFETCH_DIST
            ),
            pagingSourceFactory = {
                AuthorPagingSource(authorService)
            }
        ).flow
            .flowOn(Dispatchers.IO)
    }

    @WorkerThread
    fun fetchTop10Authors() = flow {
        authorService.getAuthors(1)
            .suspendOnSuccess {
                emit(data)
            }
            .suspendOnError(GetAuthorErrorResponseMapper) {
                emit(this)
            }
            .suspendOnException {
                emit(null)
            }
    }.flowOn(Dispatchers.IO)
}
