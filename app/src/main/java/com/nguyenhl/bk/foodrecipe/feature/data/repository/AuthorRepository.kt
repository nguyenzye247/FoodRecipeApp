package com.nguyenhl.bk.foodrecipe.feature.data.repository

import androidx.annotation.WorkerThread
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.GetAuthorErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.AuthorService
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AuthorRepository(
    private val authorService: AuthorService
) : Repository {
    @WorkerThread
    fun fetchAuthors() = flow {
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
