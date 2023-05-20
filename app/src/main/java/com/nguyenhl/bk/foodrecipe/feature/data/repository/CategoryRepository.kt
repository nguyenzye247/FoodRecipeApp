package com.nguyenhl.bk.foodrecipe.feature.data.repository

import androidx.annotation.WorkerThread
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.CategoryErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.category.ApiCategory
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.category.toCategory
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.category.toCategoryDetail
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.CategoryService
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao.CategoryDao
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao.CategoryDetailDao
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.CategoryDetail
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class CategoryRepository constructor(
    private val categoryService: CategoryService,
    private val categoryDao: CategoryDao,
    private val categoryDetailDao: CategoryDetailDao
) : Repository {

    @WorkerThread
    fun getApiAllCategories() = flow {
        categoryService.getAllCategories()
        categoryService.getAllCategories()
            .suspendOnSuccess {
                emit(data)
            }
            .suspendOnError(CategoryErrorResponseMapper) {
                emit(this)
            }
    }.flowOn(Dispatchers.IO)

    private suspend fun saveAllCategory(apiCategories: List<ApiCategory>) {
        val categoryForSave = apiCategories.map { it.toCategory() }
        categoryDao.insertAll(categoryForSave)
    }

    private suspend fun saveAllCategoryDetail(apiCategories: List<ApiCategory>) {
        val categoryDetails = mapAllCategoryDetailsFromApi(apiCategories)
        categoryDetailDao.insertAll(categoryDetails)
    }

    private fun mapAllCategoryDetailsFromApi(apiCategories: List<ApiCategory>): List<CategoryDetail> {
        return apiCategories.flatMap { apiCategory ->
            apiCategory.categoryDetails.map {
                it.copy(idCategory = apiCategory.idCategory).toCategoryDetail()
            }
        }
    }
}
