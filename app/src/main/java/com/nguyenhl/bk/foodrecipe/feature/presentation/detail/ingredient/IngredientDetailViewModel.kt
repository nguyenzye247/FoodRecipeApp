package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.ingredient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nguyenhl.bk.foodrecipe.core.extension.toast
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.recipe.SearchRecipeFilterBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.recipe.createSearchBodyFromIdIngredient
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.recipe.toRecipeDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.toIngredientDetailDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.IngredientDetailResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.recipe.LikeRecipeResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.recipe.RecipeResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.recipe.toApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.data.repository.IngredientRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.RecipeRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.ApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.dto.IngredientDetailDto
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto
import com.nguyenhl.bk.foodrecipe.feature.helper.SessionManager
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.usecase.HomeFetchRecipeUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class IngredientDetailViewModel(
    val input: BaseInput.IngredientDetailInput,
    private val ingredientRepository: IngredientRepository,
    private val recipeRepository: RecipeRepository
) : BaseViewModel(input) {
    private val ingredientInfo by lazy { input.ingredientDto }

    private val _ingredientDetail: MutableLiveData<IngredientDetailDto?> =
        MutableLiveData(null)
    fun getIngredientDetail(): LiveData<IngredientDetailDto?> = _ingredientDetail

    private val _ingredientRecipes: MutableLiveData<List<RecipeDto>?> = MutableLiveData()
    fun liveIngredientRecipes(): LiveData<List<RecipeDto>?> = _ingredientRecipes

    private val _likeRecipe: MutableLiveData<ApiCommonResponse?> = MutableLiveData()
    fun liveLikeRecipe(): LiveData<ApiCommonResponse?> = _likeRecipe
    fun setLikeRecipeValue(value: ApiCommonResponse?) {
        _likeRecipe.postValue(value)
    }

    init {
        fetchIngredientDetail()
        fetchTop10RecipeByIngredient()
    }

    private fun fetchIngredientDetail() {
        viewModelScope.launch(Dispatchers.IO) {
            ingredientInfo?.let { ingredient ->
                ingredientRepository.fetchIngredientDetail(ingredient.idIngredientDetail)
                    .collectLatest { response ->
                        when (response) {
                            is IngredientDetailResponse -> {
                                _ingredientDetail.postValue(response.detail.toIngredientDetailDto())
                            }

                            else -> {
                                _ingredientDetail.postValue(null)
                            }
                        }
                    }
            }
        }
    }

    private fun fetchTop10RecipeByIngredient() {
        val token = SessionManager.fetchToken(input.application).ifEmpty {
            input.application.toast("Empty token")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            ingredientInfo?.let { ingredient ->
                recipeRepository.searchTop10RecipeByFilters(
                    token,
                    createSearchBodyFromIdIngredient(ingredient.idIngredient)
                ).collect { response ->
                    when (response) {
                        is RecipeResponse -> {
                            _ingredientRecipes.postValue(response.recipes.map { it.toRecipeDto() })
                        }

                        else -> {
                            _ingredientRecipes.postValue(null)
                        }
                    }
                }
            }
        }
    }

    fun likeRecipe(recipe: RecipeDto) {
        val token = SessionManager.fetchToken(input.application).ifEmpty {
            input.application.toast("Empty token")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            recipeRepository.likeRecipe(token, recipe.idRecipe).collectLatest { response ->
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
}