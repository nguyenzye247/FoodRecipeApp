package com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.suggest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nguyenhl.bk.foodrecipe.core.extension.toast
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.recipe.SearchRecipeFilterBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.recipe.LikeRecipeResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.recipe.toApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.data.repository.RecipeRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.UserInfoRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.search.SearchRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.ApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto
import com.nguyenhl.bk.foodrecipe.feature.dto.UserInfoDto
import com.nguyenhl.bk.foodrecipe.feature.helper.SessionManager
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.BaseViewAllViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class VASuggestViewModel constructor(
    val input: BaseInput.BaseViewAllInput.ViewAllSuggestInput,
    private val userInfoRepository: UserInfoRepository,
    private val searchRepository: SearchRepository,
    private val recipeRepository: RecipeRepository
) : BaseViewAllViewModel(input) {
    private val userInfoDto: UserInfoDto? = input.userInfo

    private val _suggestRecipePaging: MutableStateFlow<PagingData<RecipeDto>?> =
        MutableStateFlow(null)
    fun getSuggestRecipesPaging(): StateFlow<PagingData<RecipeDto>?> = _suggestRecipePaging

    private val _likeRecipe: MutableLiveData<ApiCommonResponse?> = MutableLiveData()
    fun liveLikeRecipe(): LiveData<ApiCommonResponse?> = _likeRecipe
    fun setLikeRecipeValue(value: ApiCommonResponse?) {
        _likeRecipe.postValue(value)
    }

    init {
        viewModelScope.launch {
            fetchSuggestRecipes()
        }
    }

    private suspend fun fetchSuggestRecipes() {
        val token = SessionManager.fetchToken(input.application).ifEmpty {
            input.application.toast("Empty token")
            return
        }
        userInfoDto ?: return
        val userInfo = userInfoRepository.getUserInfoByUserId(userInfoDto.userId) ?: return

        val categoryDetails = userInfo.healthStatusWithCategoryDetail.categoryDetails
        val searchRecipeFilterBody = SearchRecipeFilterBody(
            idCategoryDetails = categoryDetails.map { it.idCategoryDetail },
            null,
            null,
            null,
            null
        )

        searchRepository.searchRecipeByFilters(token, searchRecipeFilterBody)
            .cachedIn(viewModelScope)
            .collect {
                _suggestRecipePaging.value = it
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
