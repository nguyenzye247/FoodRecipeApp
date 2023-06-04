package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.pagingsource

import android.content.Context
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.pagingsource.model.PagingDataItem

class ViewMoreSuggestPagingSource(
    private val context: Context,

): BasePagingSource(context) {
    override fun toLoadResult(): LoadResult<Int, PagingDataItem> {

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PagingDataItem> {
        TODO("Not yet implemented")
    }

}
