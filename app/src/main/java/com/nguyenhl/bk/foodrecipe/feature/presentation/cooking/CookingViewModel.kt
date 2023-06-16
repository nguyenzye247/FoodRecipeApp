package com.nguyenhl.bk.foodrecipe.feature.presentation.cooking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.dto.IngredientDto
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDetailDto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CookingViewModel(val input: BaseInput.CookingInput): BaseViewModel(input) {
    private val _recipeDetail: MutableLiveData<RecipeDetailDto?> =
        MutableLiveData(null)
    fun liveRecipeDetail(): LiveData<RecipeDetailDto?> = _recipeDetail

    private val _recipeIngredientDetail: MutableLiveData<List<IngredientDto>?> =
        MutableLiveData(null)
    fun liveRecipeIngredientDetail(): LiveData<List<IngredientDto>?> = _recipeIngredientDetail

    private val _cookingButtonType: MutableStateFlow<CookingButtonType?> = MutableStateFlow(null)
    fun flowCookingButtonType(): StateFlow<CookingButtonType?> = _cookingButtonType
    fun setCookingButtonType(type: CookingButtonType) {
        _cookingButtonType.value = type
    }

    init {
        _recipeDetail.value = input.recipeDetailDto
        _recipeIngredientDetail.value = input.ingredients
    }
}
