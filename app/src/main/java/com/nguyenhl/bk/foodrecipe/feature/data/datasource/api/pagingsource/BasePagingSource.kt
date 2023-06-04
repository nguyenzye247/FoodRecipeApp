package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.pagingsource

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.pagingsource.model.PagingDataItem

abstract class BasePagingSource(
    private val context: Context
) : PagingSource<Int, PagingDataItem>() {
    override fun getRefreshKey(state: PagingState<Int, PagingDataItem>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        } ?: kotlin.run { null }
    }

    abstract fun toLoadResult(): LoadResult<Int, PagingDataItem>

    companion object {
        private const val DEFAULT_PAGE_INDEX = 1
    }
}
