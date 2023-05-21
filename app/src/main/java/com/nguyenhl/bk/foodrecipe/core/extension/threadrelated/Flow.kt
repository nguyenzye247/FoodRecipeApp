package com.nguyenhl.bk.foodrecipe.core.extension.threadrelated

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.BaseApiErrorModelMapper
import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

suspend fun <RP> ApiResponse<RP>.suspendOnStatus(errorMapper: BaseApiErrorModelMapper): Flow<Any?> {
    return flow {
        suspendOnSuccess {
            emit(data)
        }
        suspendOnError(errorMapper) {
            emit(this)
        }
        suspendOnException {
            emit(null)
        }
    }
}
