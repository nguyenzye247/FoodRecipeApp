package com.nguyenhl.bk.foodrecipe.feature.presentation.search

import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.recipe.SearchRecipeFilterBody
import com.nguyenhl.bk.foodrecipe.feature.data.repository.RecipeRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.search.SearchRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class SearchUseCase constructor(
    private val recipeRepository: RecipeRepository,
    private val searchRepository: SearchRepository
) {

    private val _searchRecipePaging: MutableStateFlow<PagingData<RecipeDto>?> =
        MutableStateFlow(null)

    fun getRandomRecipesPaging(): StateFlow<PagingData<RecipeDto>?> = _searchRecipePaging
    fun setRandomRecipePagingValue(value: PagingData<RecipeDto>?) {
        _searchRecipePaging.value = value
    }

    suspend fun searchRecipe(
        token: String,
        searchRecipeFilterBody: SearchRecipeFilterBody,
        viewModelScope: CoroutineScope
    ) {
        searchRepository.searchRecipeByFilters(token, searchRecipeFilterBody)
            .cachedIn(viewModelScope)
            .collect {
                _searchRecipePaging.value = it
            }
    }

    suspend fun fetchRandomRecipes(token: String, viewModelScope: CoroutineScope) {
        recipeRepository.fetchRandomRecipes(token)
            .cachedIn(viewModelScope)
            .collect {
                _searchRecipePaging.value = it
            }
    }
}
