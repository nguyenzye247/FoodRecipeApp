package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.CategoryReponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface CategoryService {
    companion object {
        const val CATEGORY_GET_EP = "category"
    }

    @GET(CATEGORY_GET_EP)
    suspend fun getAllCategories(): ApiResponse<CategoryReponse>
}
