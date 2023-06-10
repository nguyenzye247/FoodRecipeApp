package com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.random

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nguyenhl.bk.foodrecipe.core.extension.toast
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.data.repository.RecipeRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto
import com.nguyenhl.bk.foodrecipe.feature.helper.SessionManager
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.BaseViewAllViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VARandomRecipeViewModel(
    val input: BaseInput.BaseViewAllInput.ViewAllRandomInput,
    private val recipeRepository: RecipeRepository
) : BaseViewAllViewModel(input) {

    private val _randomRecipePaging: MutableStateFlow<PagingData<RecipeDto>?> =
        MutableStateFlow(null)
    fun getRandomRecipesPaging(): StateFlow<PagingData<RecipeDto>?> = _randomRecipePaging

    init {
        viewModelScope.launch {
            fetchRandomRecipes()
        }
    }

    private suspend fun fetchRandomRecipes() {
        val token = SessionManager.fetchToken(input.application).ifEmpty {
            input.application.toast("Empty token")
            return
        }

        recipeRepository.fetchRandomRecipes(token)
            .cachedIn(viewModelScope)
            .collect {
                _randomRecipePaging.value = it
            }
    }
}
