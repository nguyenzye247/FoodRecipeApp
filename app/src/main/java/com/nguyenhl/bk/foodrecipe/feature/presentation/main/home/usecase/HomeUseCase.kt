package com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.category.toCategory
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.category.toCategoryDetails
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.CategoryResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.recipe.LikeRecipeResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.recipe.RemoveRecipeResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.recipe.toApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.Recipe
import com.nguyenhl.bk.foodrecipe.feature.data.repository.CategoryRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.HealthStatusRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.RecipeRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.ApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

class HomeUseCase constructor(
    private val healthStatusRepository: HealthStatusRepository,
    private val categoryRepository: CategoryRepository,
    private val recipeRepository: RecipeRepository
) {
    private val _likeRecipe: MutableLiveData<ApiCommonResponse?> = MutableLiveData()
    fun liveLikeRecipe(): LiveData<ApiCommonResponse?> = _likeRecipe
    fun setLikeRecipeValue(value: ApiCommonResponse?) {
        _likeRecipe.postValue(value)
    }
    private val _removeCalendarRecipe: MutableLiveData<ApiCommonResponse?> = MutableLiveData()
    fun liveRemoveCalendarRecipe(): LiveData<ApiCommonResponse?> = _removeCalendarRecipe
    fun setRemoveCalendarRecipeValue(value: ApiCommonResponse?) {
        _removeCalendarRecipe.postValue(value)
    }

    private val _likedRecipesPaging: MutableStateFlow<PagingData<RecipeDto>?> =
        MutableStateFlow(null)
    fun geLikedRecipesPaging(): StateFlow<PagingData<RecipeDto>?> = _likedRecipesPaging

    suspend fun getAllCategories() {
        categoryRepository.getApiAllCategories().collectLatest { response ->
            when (response) {
                is CategoryResponse -> {
                    saveAllCategories(response)
                }

                else -> {

                }
            }
        }
    }

    suspend fun likeRecipe(token: String, recipeId: String) {
        recipeRepository.likeRecipe(token, recipeId).collectLatest { response ->
            when (response) {
                is LikeRecipeResponse -> {
                    setLikeRecipeValue(response.toApiCommonResponse())
                }

                else -> {
                    setLikeRecipeValue(null)
                }
            }
        }
    }

    suspend fun removeRecipe(token: String, recipeId: String) {
        recipeRepository.removeCalendarRecipe(token, recipeId).collectLatest { response ->
            when (response) {
                is RemoveRecipeResponse -> {
                    setRemoveCalendarRecipeValue(response.toApiCommonResponse())
                }

                else -> {
                    setRemoveCalendarRecipeValue(null)
                }
            }
        }
    }

    suspend fun fetchLikedRecipes(token: String, viewModelScope: CoroutineScope) {
        recipeRepository.fetchLikedRecipes(token)
            .cachedIn(viewModelScope)
            .collect {
                _likedRecipesPaging.value = it
            }
    }

    private suspend fun saveAllCategories(response: CategoryResponse) {
        val categories = response.data.map { it.toCategory() }
        val categoryDetails = response.data.flatMap { apiCategory ->
            apiCategory.toCategoryDetails()
        }
        categoryRepository.saveAllCategory(categories)
        categoryRepository.saveAllCategoryDetail(categoryDetails)
    }

    suspend fun updateRecipe(recipe: Recipe) {
        recipeRepository.updateRecipe(recipe)
    }
}
