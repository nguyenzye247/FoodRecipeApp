package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.recipe.toNutrientDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.recipe.toRecipeDetailDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.toIngredientDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.recipe.RecipeDetailResponse
import com.nguyenhl.bk.foodrecipe.feature.data.repository.RecipeRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.IngredientDto
import com.nguyenhl.bk.foodrecipe.feature.dto.NutrientDto
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDetailDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipeDetailViewModel(
    val input: BaseInput.RecipeDetailInput,
    private val recipeRepository: RecipeRepository
) : BaseViewModel(input) {
    private val recipeDto by lazy { input.recipeDto }

    private val _recipeDetail: MutableLiveData<RecipeDetailDto?> =
        MutableLiveData(null)
    fun liveRecipeDetail(): LiveData<RecipeDetailDto?> = _recipeDetail

    private val _recipeIngredientDetail: MutableLiveData<List<IngredientDto>?> =
        MutableLiveData(null)
    fun liveRecipeIngredientDetail(): LiveData<List<IngredientDto>?> = _recipeIngredientDetail

    private val _recipeNutrientDetail: MutableLiveData<List<NutrientDto>?> =
        MutableLiveData(null)
    fun liveRecipeNutrientDetail(): LiveData<List<NutrientDto>?> = _recipeNutrientDetail

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchRecipeDetails()
        }
    }

    private suspend fun fetchRecipeDetails() {
        recipeDto?.let { recipe ->
            recipeRepository.fetchRecipeDetail(recipe.idRecipeDetail)
                .collect { response ->
                    setLoading(false)
                    when (response) {
                        is RecipeDetailResponse -> {
                            _recipeDetail.value = response.recipeDetail.toRecipeDetailDto()
                            _recipeIngredientDetail.value =
                                response.ingredients.map { it.toIngredientDto() }
                            _recipeNutrientDetail.value =
                                response.nutrients.map { it.toNutrientDto() }
                        }

                        else -> {

                        }
                    }
                }
        }
    }
}
