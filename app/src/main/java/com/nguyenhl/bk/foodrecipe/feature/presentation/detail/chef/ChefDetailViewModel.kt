package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.chef

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nguyenhl.bk.foodrecipe.core.extension.toast
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.recipe.LikeRecipeResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.recipe.toApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.data.repository.RecipeRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.ApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto
import com.nguyenhl.bk.foodrecipe.feature.helper.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ChefDetailViewModel(
    val input: BaseInput.ChefDetailInput,
    private val recipeRepository: RecipeRepository
) : BaseViewModel(input) {
    private val authorInfo by lazy { input.authorDto }

    private val _chefRecipesPaging: MutableStateFlow<PagingData<RecipeDto>?> =
        MutableStateFlow(null)
    fun getChefRecipesPaging(): StateFlow<PagingData<RecipeDto>?> = _chefRecipesPaging

    private val _likeRecipe: MutableLiveData<ApiCommonResponse?> = MutableLiveData()
    fun liveLikeRecipe(): LiveData<ApiCommonResponse?> = _likeRecipe
    fun setLikeRecipeValue(value: ApiCommonResponse?) {
        _likeRecipe.postValue(value)
    }

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