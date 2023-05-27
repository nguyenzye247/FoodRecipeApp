package com.nguyenhl.bk.foodrecipe.feature.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Job
import kotlinx.coroutines.async

class DispatchGroup(
    private val dispatcherScope: CoroutineDispatcher
) {
    private val job = Job()
    private val scope = CoroutineScope(dispatcherScope + job)

    @Suppress("DeferredResultUnused")
    suspend fun <T> async(block: suspend CoroutineScope.() -> T): Deferred<T> {
        return scope.async { block() }
    }

    suspend fun awaitAll(onCompleted: () -> Unit) {
        job.children.forEach { it.join() }
        onCompleted.invoke()
    }
}
