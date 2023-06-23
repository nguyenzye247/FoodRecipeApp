package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.collection

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
import com.nguyenhl.bk.foodrecipe.feature.data.repository.CollectionRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.RecipeRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.ApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto
import com.nguyenhl.bk.foodrecipe.feature.helper.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CollectionDetailViewModel(
    val input: BaseInput.CollectionDetailInput,
    private val recipeRepository: RecipeRepository
): BaseViewModel(input) {
    private val collectionInfo by lazy { input.collectionDto }

    private val _collectionRecipesPaging: MutableStateFlow<PagingData<RecipeDto>?> =
        MutableStateFlow(null)
    fun getCollectionRecipesPaging(): StateFlow<PagingData<RecipeDto>?> = _collectionRecipesPaging

    private val _likeRecipe: MutableLiveData<ApiCommonResponse?> = MutableLiveData()
    fun liveLikeRecipe(): LiveData<ApiCommonResponse?> = _likeRecipe
    fun setLikeRecipeValue(value: ApiCommonResponse?) {
        _likeRecipe.postValue(value)
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchRecipesByCollection()
        }
    }

    private suspend fun fetchRecipesByCollection() {
        val token = SessionManager.fetchToken(input.application).ifEmpty {
            input.application.toast("Empty token")
            return
        }
        collectionInfo?.let { collection ->
            recipeRepository.fetchRecipesByCollection(token, collection.idCollection)
                .cachedIn(viewModelScope)
                .collect {
                    _collectionRecipesPaging.value = it
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