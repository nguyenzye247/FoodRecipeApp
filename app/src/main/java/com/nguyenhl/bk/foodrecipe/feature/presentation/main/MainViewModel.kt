package com.nguyenhl.bk.foodrecipe.feature.presentation.main

import androidx.lifecycle.viewModelScope
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.category.toCategory
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.category.toCategoryDetails
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.toHealthStatus
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.toHealthStatusCategoryDetails
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.CategoryReponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.HealthStatusResponse
import com.nguyenhl.bk.foodrecipe.feature.data.repository.CategoryRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.HealthStatusRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel(
    val input: BaseInput.MainInput,
    private val healthStatusRepository: HealthStatusRepository,
    private val categoryRepository: CategoryRepository
) : BaseViewModel(input) {

    init {
        viewModelScope.launch {
            getAllCategories()
            getAllHealthStatuses()
        }
    }

    private suspend fun getAllHealthStatuses() {
        healthStatusRepository.getApiAllHealthStatus().collectLatest { response ->
            when (response) {
                is HealthStatusResponse -> {
                    saveAllHealthStatuses(response)
                }

                else -> {

                }
            }
        }
    }

    private suspend fun getAllCategories() {
        categoryRepository.getApiAllCategories().collectLatest { response ->
            when (response) {
                is CategoryReponse -> {
                    saveAllCategories(response)
                }

                else -> {

                }
            }
        }
    }

    private suspend fun saveAllHealthStatuses(response: HealthStatusResponse) {
        val healthStatuses = response.data.map { it.toHealthStatus() }
        val healthStatusCatDetails = response.data.flatMap { it.toHealthStatusCategoryDetails() }
        healthStatusRepository.saveAllHealthStatuses(healthStatuses)
        healthStatusRepository.saveAllHealthStatusCatDetails(healthStatusCatDetails)
    }

    private suspend fun saveAllCategories(response: CategoryReponse) {
        val categories = response.data.map { it.toCategory() }
        val categoryDetails = response.data.flatMap { apiCategory ->
            apiCategory.toCategoryDetails()
        }
        categoryRepository.saveAllCategory(categories)
        categoryRepository.saveAllCategoryDetail(categoryDetails)
    }

    fun init() {

    }
}
