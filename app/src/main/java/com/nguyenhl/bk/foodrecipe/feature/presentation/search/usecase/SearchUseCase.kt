package com.nguyenhl.bk.foodrecipe.feature.presentation.search.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.recipe.SearchRecipeFilterBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.recipe.LikeRecipeResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.recipe.toApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.CategoryDetail
import com.nguyenhl.bk.foodrecipe.feature.data.repository.CategoryRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.RecipeRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.search.SearchRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.ApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collectLatest

class SearchUseCase constructor(
    private val recipeRepository: RecipeRepository,
    private val searchRepository: SearchRepository,
    private val categoryDetailRepository: CategoryRepository
) {

    private val _searchRecipePaging: MutableStateFlow<PagingData<RecipeDto>?> =
        MutableStateFlow(null)
    fun getSearchRecipesPaging(): StateFlow<PagingData<RecipeDto>?> = _searchRecipePaging
    fun setSearchRecipePagingValue(value: PagingData<RecipeDto>?) {
        _searchRecipePaging.value = value
    }

    private val _suggestRecipePaging: MutableStateFlow<PagingData<RecipeDto>?> =
        MutableStateFlow(null)
    fun getSuggestRecipesPaging(): StateFlow<PagingData<RecipeDto>?> = _suggestRecipePaging
    fun setSuggestRecipePagingValue(value: PagingData<RecipeDto>?) {
        _suggestRecipePaging.value = value
    }

    private val _likeRecipe: MutableLiveData<ApiCommonResponse?> = MutableLiveData()
    fun liveLikeRecipe(): LiveData<ApiCommonResponse?> = _likeRecipe
    fun setLikeRecipeValue(value: ApiCommonResponse?) {
        _likeRecipe.postValue(value)
    }

    suspend fun searchRecipe(
        token: String,
        searchRecipeFilterBody: SearchRecipeFilterBody,
        viewModelScope: CoroutineScope
    ) {
        searchRepository.searchRecipeByFilters(token, searchRecipeFilterBody)
            .cachedIn(viewModelScope)
            .collect {
                setSearchRecipePagingValue(it)
            }
    }

    suspend fun searchSuggestRecipe(
        token: String,
        searchRecipeFilterBody: SearchRecipeFilterBody,
        viewModelScope: CoroutineScope
    ) {
        searchRepository.searchRecipeByFilters(token, searchRecipeFilterBody)
            .cachedIn(viewModelScope)
            .collect {
                setSuggestRecipePagingValue(it)
            }
    }

    suspend fun fetchRandomRecipes(token: String, viewModelScope: CoroutineScope) {
        recipeRepository.fetchRandomRecipes(token)
            .cachedIn(viewModelScope)
            .collect {
                setSearchRecipePagingValue(it)
            }
    }

    suspend fun getUserHealthCategoryDetails(): List<CategoryDetail>? {
        return categoryDetailRepository.getUserHealthCategoryDetails()
    }

    suspend fun likeRecipe(token: String, recipeId: String) {
        recipeRepository.likeRecipe(token, recipeId).collectLatest { response ->
            when (response) {
                is LikeRecipeResponse -> {
                    setLikeRecipeValue(response.toApiCommonResponse())
                }

                else -> {
                    _likeRecipe.postValue(null)
                }
            }
        }
    }
}
