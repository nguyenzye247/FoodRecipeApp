package com.nguyenhl.bk.foodrecipe.feature.presentation.cooking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.dto.IngredientDto
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDetailDto

class CookingViewModel(val input: BaseInput.CookingInput): BaseViewModel(input) {
    private val _recipeDetail: MutableLiveData<RecipeDetailDto?> =
        MutableLiveData(null)
    fun liveRecipeDetail(): LiveData<RecipeDetailDto?> = _recipeDetail

    private val _recipeIngredientDetail: MutableLiveData<List<IngredientDto>?> =
        MutableLiveData(null)
    fun liveRecipeIngredientDetail(): LiveData<List<IngredientDto>?> = _recipeIngredientDetail

    init {
        _recipeDetail.value = input.recipeDetailDto
        _recipeIngredientDetail.value = input.ingredients
    }
}
