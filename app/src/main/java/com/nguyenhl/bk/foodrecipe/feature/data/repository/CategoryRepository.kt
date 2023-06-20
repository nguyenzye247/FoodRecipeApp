package com.nguyenhl.bk.foodrecipe.feature.data.repository

import androidx.annotation.WorkerThread
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.GlobalRetryPolicy
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.CategoryErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.category.ApiCategory
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.category.toCategory
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.category.toCategoryDetail
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.CategoryService
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao.CategoryDao
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.dao.CategoryDetailDao
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.Category
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.CategoryDetail
import com.skydoves.sandwich.retry.runAndRetry
import com.skydoves.sandwich.suspendOnError
import com.skydoves.sandwich.suspendOnException
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
        runAndRetry(GlobalRetryPolicy()) { _, _ ->
            categoryService.getAllCategories()
                .suspendOnSuccess {
                    emit(data)
                }
                .suspendOnError(CategoryErrorResponseMapper) {
                    emit(this)
                }
                .suspendOnException {
                    emit(null)
                }
        }
    }.flowOn(Dispatchers.IO)

    suspend fun saveAllCategory(categories: List<Category>) {
        categoryDao.insertAll(categories)
    }

    suspend fun saveAllCategoryDetail(categoryDetails: List<CategoryDetail>) {
        categoryDetailDao.insertAll(categoryDetails)
    }

    private fun mapAllCategoryDetailsFromApi(apiCategories: List<ApiCategory>): List<CategoryDetail> {
        return apiCategories.flatMap { apiCategory ->
            apiCategory.categoryDetails.map {
                it.copy(idCategory = apiCategory.idCategory).toCategoryDetail()
            }
        }
    }

    suspend fun getRandomCategoryDetails(amount: Int): List<CategoryDetail>? {
        return categoryDetailDao.getRandomCategoryDetail(amount)
    }

    suspend fun getCategoryDetailsByCategoryId(idCategory: String, amount: Int): List<CategoryDetail>? {
        return categoryDetailDao.getCategoryDetailsByCategoryId(idCategory, amount)
    }

    suspend fun getUserHealthCategoryDetails(): List<CategoryDetail>? {
        return categoryDetailDao.getAllUserHealthCategoryDetails()
    }
}
