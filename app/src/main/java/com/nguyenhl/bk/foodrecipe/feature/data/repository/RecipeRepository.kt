package com.nguyenhl.bk.foodrecipe.feature.data.repository

import androidx.annotation.WorkerThread
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.nguyenhl.bk.foodrecipe.core.common.INITIAL_LOAD_SIZE
import com.nguyenhl.bk.foodrecipe.core.common.MAIN_RECIPE_PAGE
import com.nguyenhl.bk.foodrecipe.core.common.PAGE_SIZE
import com.nguyenhl.bk.foodrecipe.core.common.PREFETCH_DIST
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.recipe.SearchRecipeFilterBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.GetRandomRecipeErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.SearchRecipeByFiltersErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.recipe.ApiRecipe
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.pagingsource.RandomRecipePagingSource
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.pagingsource.SuggestRecipePagingSource
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.RecipeService
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RecipeRepository constructor(
    private val recipeService: RecipeService
) : Repository {

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
                SuggestRecipePagingSource(
                    token,
                    searchRecipeFilterBody,
                    recipeService
                )
            }
        ).flow
            .flowOn(Dispatchers.IO)
    }

    @WorkerThread
    fun fetchRandomRecipes(
        token: String
    ): Flow<PagingData<RecipeDto>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = INITIAL_LOAD_SIZE,
                prefetchDistance = PREFETCH_DIST
            ),
            pagingSourceFactory = {
                RandomRecipePagingSource(
                    token,
                    recipeService
                )
            }
        ).flow
            .flowOn(Dispatchers.IO)
    }

    @WorkerThread
    fun searchTop10RecipeByFilters(token: String, searchRecipeFilterBody: SearchRecipeFilterBody) =
        flow {
            recipeService.searchRecipeByFilters(token, searchRecipeFilterBody, MAIN_RECIPE_PAGE)
                .suspendOnSuccess {
                    emit(data)
                }
                .suspendOnError(SearchRecipeByFiltersErrorResponseMapper) {
                    emit(this)
                }
                .suspendOnException {
                    emit(null)
                }
        }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun fetchTop10RandomRecipes(token: String) = flow {
        recipeService.getRandomRecipes(token)
            .suspendOnSuccess {
                emit(data)
            }
            .suspendOnError(GetRandomRecipeErrorResponseMapper) {
                emit(this)
            }
            .suspendOnException {
                emit(null)
            }
    }.flowOn(Dispatchers.IO)
}
