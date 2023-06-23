package com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nguyenhl.bk.foodrecipe.core.extension.toast
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.category.toCategory
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.category.toCategoryDetails
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.CategoryResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.recipe.LikeRecipeResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.recipe.toApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.Recipe
import com.nguyenhl.bk.foodrecipe.feature.data.repository.CategoryRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.HealthStatusRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.RecipeRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.ApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.dto.CommonResponseData
import com.nguyenhl.bk.foodrecipe.feature.helper.SessionManager
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
