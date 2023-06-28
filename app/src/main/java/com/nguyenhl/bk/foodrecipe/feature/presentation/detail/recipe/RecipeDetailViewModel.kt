package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nguyenhl.bk.foodrecipe.core.extension.toast
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.recipe.toNutrientDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.recipe.toRecipeDetailDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.toIngredientDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.recipe.LikeRecipeResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.recipe.RecipeDetailResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.recipe.toApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.data.repository.RecipeRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.*
import com.nguyenhl.bk.foodrecipe.feature.helper.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
    val input: BaseInput.RecipeDetailInput,
    private val recipeRepository: RecipeRepository
) : BaseViewModel(input) {
    private val recipeDto by lazy { input.recipeDto }

    private val _recipeDetail: MutableLiveData<RecipeDetailDto?> =
        MutableLiveData(null)
    fun getRecipeDetailValue(): RecipeDetailDto? = _recipeDetail.value
    fun liveRecipeDetail(): LiveData<RecipeDetailDto?> = _recipeDetail

    private val _recipeIngredientDetail: MutableLiveData<List<IngredientDto>?> =
        MutableLiveData(null)
    fun getIngredientDetailValue(): List<IngredientDto>? = _recipeIngredientDetail.value
    fun liveRecipeIngredientDetail(): LiveData<List<IngredientDto>?> = _recipeIngredientDetail

    private val _recipeNutrientDetail: MutableLiveData<List<NutrientDto>?> =
        MutableLiveData(null)
    fun liveRecipeNutrientDetail(): LiveData<List<NutrientDto>?> = _recipeNutrientDetail

    private val _likeRecipe: MutableLiveData<ApiCommonResponse?> = MutableLiveData()
    fun liveLikeRecipe(): LiveData<ApiCommonResponse?> = _likeRecipe
    fun setLikeRecipeValue(value: ApiCommonResponse?) {
        _likeRecipe.postValue(value)
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchRecipeDetails()
            insertRecentlyViewedRecipe(input.recipeDto)
        }
    }

    private suspend fun fetchRecipeDetails() {
        recipeDto?.let { recipe ->
            recipeRepository.fetchRecipeDetail(recipe.idRecipeDetail)
                .collect { response ->
                    setLoading(false)
                    when (response) {
                        is RecipeDetailResponse -> {
                            _recipeDetail
                                .postValue(response.recipeDetail.toRecipeDetailDto())
                            _recipeIngredientDetail
                                .postValue(response.ingredients.map { it.toIngredientDto() })
                            _recipeNutrientDetail
                                .postValue(response.nutrients.map { it.toNutrientDto() })
                        }

                        else -> {

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

    private suspend fun insertRecentlyViewedRecipe(recipe: RecipeDto?) {
        recipe ?: return
        recipeRepository.insertRecipe(recipe.toRecipe())
    }
}
