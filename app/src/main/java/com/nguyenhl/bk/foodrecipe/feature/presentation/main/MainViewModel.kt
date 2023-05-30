package com.nguyenhl.bk.foodrecipe.feature.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.nguyenhl.bk.foodrecipe.core.extension.getBaseConfig
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.Collection
import com.nguyenhl.bk.foodrecipe.feature.dto.CollectionDto
import com.nguyenhl.bk.foodrecipe.feature.dto.DishPreferredDto
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto
import com.nguyenhl.bk.foodrecipe.feature.dto.UserInfoDto
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.usecase.HomeFetchRecipeUseCase
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.usecase.HomeUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    val input: BaseInput.MainInput,
    private val homeUseCase: HomeUseCase,
    private val homeFetchRecipeUseCase: HomeFetchRecipeUseCase
) : BaseViewModel(input) {
    private val userId: String = input.application.getBaseConfig().userId

    init {
        setLoading(true)
        viewModelScope.launch {
            homeUseCase.getAllCategories()
            homeFetchRecipeUseCase.fetchRecipeData(input.application, userId) {
                // on Finish
                setLoading(false)
            }
        }
    }

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

    fun init() {

    }
}
