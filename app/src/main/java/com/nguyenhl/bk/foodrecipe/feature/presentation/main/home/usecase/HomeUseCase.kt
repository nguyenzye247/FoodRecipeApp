package com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.usecase

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.category.toCategory
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.category.toCategoryDetails
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.CategoryResponse
import com.nguyenhl.bk.foodrecipe.feature.data.repository.CategoryRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.HealthStatusRepository
import kotlinx.coroutines.flow.collectLatest

class HomeUseCase constructor(
    private val healthStatusRepository: HealthStatusRepository,
    private val categoryRepository: CategoryRepository
) {

    suspend fun getAllCategories() {
        categoryRepository.getApiAllCategories().collectLatest { response ->
            when (response) {
                is CategoryResponse -> {
                    saveAllCategories(response)
                }

                else -> {

                }
            }
        }
    }

    private suspend fun saveAllCategories(response: CategoryResponse) {
        val categories = response.data.map { it.toCategory() }
        val categoryDetails = response.data.flatMap { apiCategory ->
            apiCategory.toCategoryDetails()
        }
        categoryRepository.saveAllCategory(categories)
        categoryRepository.saveAllCategoryDetail(categoryDetails)
    }
}
