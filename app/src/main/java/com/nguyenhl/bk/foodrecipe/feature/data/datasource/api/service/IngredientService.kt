package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.IngredientResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface IngredientService {
    companion object {
        const val INGREDIENT_GET_EP = "ingredient"
    }

    @GET(INGREDIENT_GET_EP)
    suspend fun getIngredients(): ApiResponse<IngredientResponse>
}
