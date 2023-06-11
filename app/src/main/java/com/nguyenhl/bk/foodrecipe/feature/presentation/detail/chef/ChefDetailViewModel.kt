package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.chef

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nguyenhl.bk.foodrecipe.core.extension.toast
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.repository.RecipeRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto
import com.nguyenhl.bk.foodrecipe.feature.helper.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ChefDetailViewModel(
    val input: BaseInput.ChefDetailInput,
    private val recipeRepository: RecipeRepository
) : BaseViewModel(input) {
    private val authorInfo by lazy { input.authorDto }

    private val _chefRecipesPaging: MutableStateFlow<PagingData<RecipeDto>?> =
        MutableStateFlow(null)
    fun getChefRecipesPaging(): StateFlow<PagingData<RecipeDto>?> = _chefRecipesPaging

    init {
        viewModelScope.launch {
            fetchRecipesByChef()
        }
    }

    suspend fun fetchRecipesByChef() {
        val token = SessionManager.fetchToken(input.application).ifEmpty {
            input.application.toast("Empty token")
            return
        }
        authorInfo?.let { author ->
            recipeRepository.fetchRecipesByAuthor(token, author.name)
                .cachedIn(viewModelScope)
                .collect {
                    _chefRecipesPaging.value = it
                }
        }
    }
}