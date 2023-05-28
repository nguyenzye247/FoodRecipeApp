package com.nguyenhl.bk.foodrecipe.feature.presentation.main

import androidx.lifecycle.viewModelScope
import com.nguyenhl.bk.foodrecipe.core.extension.getBaseConfig
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
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
            homeFetchRecipeUseCase.fetchRecipeData(userId) {
                // on Finish
                setLoading(false)
            }
        }
    }

    fun init() {

    }
}
