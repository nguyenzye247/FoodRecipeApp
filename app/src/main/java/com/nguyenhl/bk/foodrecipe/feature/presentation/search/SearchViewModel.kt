package com.nguyenhl.bk.foodrecipe.feature.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.nguyenhl.bk.foodrecipe.core.extension.toast
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.recipe.SearchRecipeFilterBody
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto
import com.nguyenhl.bk.foodrecipe.feature.dto.enumdata.FilterS
import com.nguyenhl.bk.foodrecipe.feature.dto.searchfilter.SearchFilterItemDto
import com.nguyenhl.bk.foodrecipe.feature.helper.SessionManager
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.filter.SearchFilterUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.StateFlow
import java.util.*

class SearchViewModel(
    val input: BaseInput.SearchInput,
    private val searchUseCase: SearchUseCase,
    private val searchFilterUseCase: SearchFilterUseCase
) : BaseViewModel(input) {

    val filterHashMap = EnumMap<FilterS, List<SearchFilterItemDto>>(FilterS::class.java)
    private var searchRecipePagingJob: Job? = null

    init {
        viewModelScope.launch {
            fetchRandomRecipes()
            fetchAllSearchFilters()
        }
    }

    fun liveIngredientFilters(): LiveData<List<SearchFilterItemDto>?> =
        searchFilterUseCase.liveIngredientFilters()
    fun liveAuthorFilters(): LiveData<List<SearchFilterItemDto>?> =
        searchFilterUseCase.liveAuthorFilters()
    fun liveKcalFilters(): LiveData<List<SearchFilterItemDto>?> =
        searchFilterUseCase.liveKcalFilters()
    fun liveTotalTimeFilters(): LiveData<List<SearchFilterItemDto>?> =
        searchFilterUseCase.liveTotalTimeFilters()
    fun liveServeFilters(): LiveData<List<SearchFilterItemDto>?> =
        searchFilterUseCase.liveServeFilters()

    fun liveMealTypeFilters(): LiveData<List<SearchFilterItemDto>?> =
        searchFilterUseCase.liveMealTypeFilters()
    fun liveDietFilters(): LiveData<List<SearchFilterItemDto>?> =
        searchFilterUseCase.liveDietFilters()
    fun liveDifficultyFilters(): LiveData<List<SearchFilterItemDto>?> =
        searchFilterUseCase.liveDifficultyFilters()
    fun liveCuisineFilters(): LiveData<List<SearchFilterItemDto>?> =
        searchFilterUseCase.liveCuisineFilters()

    fun getRandomRecipePaging(): StateFlow<PagingData<RecipeDto>?> = searchUseCase.getRandomRecipesPaging()

    private suspend fun fetchAllSearchFilters() {
        viewModelScope.launch(Dispatchers.IO) {
            val deferredResults = listOf(
                async { searchFilterUseCase.fetchAllAuthorFilter() },
                async { searchFilterUseCase.fetchAllIngredientFilter() },
                async { searchFilterUseCase.fetchAllKcalFilter() },
                async { searchFilterUseCase.fetchAllServeFilter() },
                async { searchFilterUseCase.fetchAllTimeFilters() },
                async { searchFilterUseCase.getMealTypeFilters() },
                async { searchFilterUseCase.getDietFilters() },
                async { searchFilterUseCase.getDifficultyFilters() },
                async { searchFilterUseCase.getCuisineFilters() },
            )

            deferredResults.awaitAll()
        }
    }

    private fun fetchRandomRecipes() {
        val token = SessionManager.fetchToken(input.application).ifEmpty {
            input.application.toast("Empty token")
            return
        }
        searchRecipePagingJob = viewModelScope.launch(Dispatchers.IO) {
            searchUseCase.fetchRandomRecipes(token, viewModelScope)
        }
    }

    fun loadSearchRecipe(filterBody: SearchRecipeFilterBody) {
        clearSearchRecipePagingJob()
        searchRecipe(filterBody)
    }

    private fun searchRecipe(filterBody: SearchRecipeFilterBody) {
        val token = SessionManager.fetchToken(input.application).ifEmpty {
            input.application.toast("Empty token")
            return
        }
        searchRecipePagingJob = viewModelScope.launch(Dispatchers.IO) {
            searchUseCase.searchRecipe(token, filterBody, viewModelScope)
        }
    }

    private fun clearSearchRecipePagingJob() {
        if (searchRecipePagingJob != null) {
            searchRecipePagingJob?.cancel()
            searchRecipePagingJob = null
            searchUseCase.setRandomRecipePagingValue(null)
        }
    }
}
