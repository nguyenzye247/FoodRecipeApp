package com.nguyenhl.bk.foodrecipe.feature.data.repository.search

import androidx.annotation.WorkerThread
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nguyenhl.bk.foodrecipe.core.common.INITIAL_LOAD_SIZE
import com.nguyenhl.bk.foodrecipe.core.common.PAGE_SIZE
import com.nguyenhl.bk.foodrecipe.core.common.PREFETCH_DIST
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.recipe.SearchRecipeFilterBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.pagingsource.SearchRecipePagingSource
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.SearchService
import com.nguyenhl.bk.foodrecipe.feature.data.repository.Repository
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class SearchRepository constructor(
    private val searchService: SearchService
): Repository {

    @WorkerThread
    fun searchRecipeByFilters(
        token: String,
        searchRecipeFilterBody: SearchRecipeFilterBody
    ): Flow<PagingData<RecipeDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = INITIAL_LOAD_SIZE,
                prefetchDistance = PREFETCH_DIST
            ),
            pagingSourceFactory = {
                SearchRecipePagingSource(
                    token,
                    searchRecipeFilterBody,
                    searchService
                )
            }
        ).flow
            .flowOn(Dispatchers.IO)
    }
}
