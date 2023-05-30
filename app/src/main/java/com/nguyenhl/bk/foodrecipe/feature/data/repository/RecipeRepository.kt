package com.nguyenhl.bk.foodrecipe.feature.data.repository

import androidx.annotation.WorkerThread
import com.nguyenhl.bk.foodrecipe.core.common.MAIN_RECIPE_PAGE
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.recipe.SearchRecipeFilterBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.GetRandomRecipeErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.SearchRecipeByFiltersErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.RecipeService
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RecipeRepository constructor(
    private val recipeService: RecipeService
) : Repository {
    @WorkerThread
    fun searchRecipeByFilters(searchRecipeFilterBody: SearchRecipeFilterBody) = flow {
        recipeService.searchRecipeByFilters(searchRecipeFilterBody, MAIN_RECIPE_PAGE)
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
    fun fetchRandomRecipes(token: String) = flow {
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
