package com.nguyenhl.bk.foodrecipe.feature.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.nguyenhl.bk.foodrecipe.core.extension.getBaseConfig
import com.nguyenhl.bk.foodrecipe.core.extension.toast
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.dto.*
import com.nguyenhl.bk.foodrecipe.feature.dto.calendar.RecipeByDateDto
import com.nguyenhl.bk.foodrecipe.feature.dto.enumdata.MealType
import com.nguyenhl.bk.foodrecipe.feature.helper.SessionManager
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.usecase.HomeFetchRecipeUseCase
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.usecase.HomeUseCase
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.usecase.SearchMealUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.HashMap

class MainViewModel(
    val input: BaseInput.MainInput,
    private val homeUseCase: HomeUseCase,
    private val homeFetchRecipeUseCase: HomeFetchRecipeUseCase,
    private val searchMealTypeUseCase: SearchMealUseCase
) : BaseViewModel(input) {
    private val userId: String = input.application.getBaseConfig().userId
    //Selected day in calendar fragment
    var selectedDate: LocalDate = LocalDate.now()

    init {
        fetchRecipeHomeData()
    }

    fun fetchRecipeHomeData() {
        setLoading(true)
        viewModelScope.launch {
            homeUseCase.getAllCategories()
            homeFetchRecipeUseCase.fetchRecipeHomeData(input.application, userId, this) {
                // on Finish
                setLoading(false)
            }
            homeFetchRecipeUseCase.getRecentlyViewedRecipes()
        }
    }

    fun getUserInfo(): UserInfoDto? = homeFetchRecipeUseCase.getUserInfo()

    fun liveUserInfo(): LiveData<UserInfoDto?> {
        return homeFetchRecipeUseCase.liveUserInfo()
    }

    fun livePreferredDishes(): LiveData<List<DishPreferredDto>?> {
        return homeFetchRecipeUseCase.livePreferredDishes()
    }

    fun liveSuggestRecipes(): LiveData<List<RecipeDto>?> {
        return homeFetchRecipeUseCase.liveSuggestRecipes()
    }

    fun liveCollections(): LiveData<List<CollectionDto>?> {
        return homeFetchRecipeUseCase.liveCollections()
    }

    fun liveIngredients(): LiveData<List<IngredientDto>?> {
        return homeFetchRecipeUseCase.liveIngredients()
    }

    fun liveTopChefs(): LiveData<List<AuthorDto>?> {
        return homeFetchRecipeUseCase.liveAuthors()
    }

    fun liveDailyInspirations(): LiveData<List<RecipeDto>?> {
        return homeFetchRecipeUseCase.liveRandomRecipes()
    }

    fun liveRecentlyView(): LiveData<List<RecipeDto>?> {
        return homeFetchRecipeUseCase.liveRecentlyViewedRecipes()
    }

    fun liveDatesHaveRecipe(): LiveData<List<String>?> =
        searchMealTypeUseCase.liveDatesHaveRecipe()
    fun liveRecipesByDate(): LiveData<HashMap<MealType?, List<RecipeByDateDto>>?> =
        searchMealTypeUseCase.liveRecipesByDate()
    fun liveRemoveRecipeFromDate(): LiveData<ApiCommonResponse?> =
        searchMealTypeUseCase.liveRemoveRecipeFromDate()

    fun getLikedRecipesPaging(): StateFlow<PagingData<RecipeDto>?> = homeUseCase.geLikedRecipesPaging()

    fun fetchAllRecipeByDate(date: String) {
        setLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            searchMealTypeUseCase.fetchAllRecipeByDate(input.application, date)
        }
    }

    fun removeRecipeFromDate(recipeByDateId: String, date: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchMealTypeUseCase.removeRecipeFromDate(input.application, recipeByDateId)
        }
    }

    fun likeRecipe(recipe: RecipeDto) {
        val token = SessionManager.fetchToken(input.application).ifEmpty {
            input.application.toast("Empty token")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            homeUseCase.likeRecipe(token, recipe.idRecipe)
        }
    }

    fun updateRecipe(recipe: RecipeDto) {
        viewModelScope.launch(Dispatchers.IO) {
            homeUseCase.updateRecipe(recipe.toRecipe())
        }
    }

    fun fetchLikedRecipe() {
        val token = SessionManager.fetchToken(input.application).ifEmpty {
            input.application.toast("Empty token")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            homeUseCase.fetchLikedRecipes(token, this)
        }
    }

    fun init() {

    }
}
