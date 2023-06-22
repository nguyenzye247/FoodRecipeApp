package com.nguyenhl.bk.foodrecipe.feature.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.nguyenhl.bk.foodrecipe.core.extension.toast
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.recipe.SearchRecipeFilterBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.recipe.addUserHealthCategoryDetailToSearchBody
import com.nguyenhl.bk.foodrecipe.feature.dto.ApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto
import com.nguyenhl.bk.foodrecipe.feature.dto.calendar.RecipeByDateDto
import com.nguyenhl.bk.foodrecipe.feature.dto.enumdata.FilterS
import com.nguyenhl.bk.foodrecipe.feature.dto.enumdata.MealType
import com.nguyenhl.bk.foodrecipe.feature.dto.searchfilter.SearchFilterItemDto
import com.nguyenhl.bk.foodrecipe.feature.helper.SessionManager
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.usecase.SearchFilterUseCase
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.usecase.SearchMealUseCase
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.usecase.SearchUseCase
import com.nguyenhl.bk.foodrecipe.feature.util.DateFormatUtil
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.util.*

class SearchViewModel(
    val input: BaseInput.SearchInput,
    private val searchUseCase: SearchUseCase,
    private val searchFilterUseCase: SearchFilterUseCase,
    private val searchMealTypeUseCase: SearchMealUseCase
) : BaseViewModel(input) {
    // flag to check if there is any recipe being added by the user
    var haveAddedRecipe: Boolean = false
    val isMealTypeSearch = input.isMealTypeSearch

    val filterHashMap = EnumMap<FilterS, List<SearchFilterItemDto>>(FilterS::class.java)
    private val userCategoryDetailIds: ArrayList<String> = arrayListOf()

    private var searchRecipePagingJob: Job? = null
    private var suggestRecipePagingJob: Job? = null

    init {
        viewModelScope.launch {
            fetchRandomRecipes()
            fetchAllSearchFilters()

            searchUseCase.getUserHealthCategoryDetails()?.let { categoryDetails ->
                userCategoryDetailIds.clear()
                userCategoryDetailIds.addAll(categoryDetails.map { it.idCategoryDetail })
            }
        }
        searchMealTypeUseCase.setAddRecipeToDate(null)
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

    fun getSearchRecipePaging(): StateFlow<PagingData<RecipeDto>?> =
        searchUseCase.getSearchRecipesPaging()

    fun getSuggestRecipesPaging(): StateFlow<PagingData<RecipeDto>?> =
        searchUseCase.getSuggestRecipesPaging()

    fun liveAddRecipeToDate(): LiveData<ApiCommonResponse?> = searchMealTypeUseCase.liveAddRecipeToDate()

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

                async { searchMealTypeUseCase.fetchAllDateHaveRecipe(input.application) },
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
        suggestRecipePagingJob = viewModelScope.launch(Dispatchers.IO) {
            searchUseCase.searchSuggestRecipe(
                token,
                filterBody.addUserHealthCategoryDetailToSearchBody(userCategoryDetailIds),
                viewModelScope
            )
        }
    }

    private fun clearSearchRecipePagingJob() {
        if (searchRecipePagingJob != null) {
            searchRecipePagingJob?.cancel()
            searchRecipePagingJob = null
            searchUseCase.setSearchRecipePagingValue(null)
        }
        if (suggestRecipePagingJob != null) {
            searchRecipePagingJob?.cancel()
            searchRecipePagingJob = null
            searchUseCase.setSuggestRecipePagingValue(null)
        }
    }

    fun getSearchBodyFromFilters(searchText: String): SearchRecipeFilterBody {
        val idCategoryDetail =
            filterHashMap[FilterS.MEAL_TYPE]
                ?.filter { it.isSelected }?.map { it.idDetail }?.ifEmpty { null }
        val totalTime =
            filterHashMap[FilterS.TOTAL_TIME]?.firstOrNull { it.isSelected }?.value
        val idIngredients =
            filterHashMap[FilterS.INGREDIENTS]
                ?.filter { it.isSelected }?.map { it.idDetail }?.ifEmpty { null }
        val authors =
            filterHashMap[FilterS.AUTHORS]
                ?.filter { it.isSelected }?.map { it.name }?.ifEmpty { null }

        return SearchRecipeFilterBody(
            idCategoryDetail,
            totalTime,
            idIngredients,
            searchText.ifEmpty { null },
            authors,
        )
    }

    fun addRecipeToDate(idRecipe: String) {
        val date = input.date
        val mealType = input.mealType ?: return
        haveAddedRecipe = true
        viewModelScope.launch(Dispatchers.IO) {
            searchMealTypeUseCase.addRecipeToDate(input.application, idRecipe, date, mealType)
        }
    }

    fun likeRecipe(recipe: RecipeDto) {
        val token = SessionManager.fetchToken(input.application).ifEmpty {
            input.application.toast("Empty token")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            searchUseCase.likeRecipe(token, recipe.idRecipe)
        }
    }
}
