package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState

abstract class BasePagingSource<D : Any, R : Any> : PagingSource<Int, D>() {
    override fun getRefreshKey(state: PagingState<Int, D>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        } ?: kotlin.run { null }
    }

    abstract fun toLoadResult(response: R, position: Int): LoadResult<Int, D>

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
    }
}
