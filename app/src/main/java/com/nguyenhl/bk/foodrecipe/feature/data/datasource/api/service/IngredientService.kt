package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.IngredientResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface IngredientService {
    companion object {
        const val INGREDIENT_GET_EP = "ingredient"
        const val INGREDIENT_SEARCH_GET_EP = "ingredient/search"
        const val INGREDIENT_ALPHA_GET_EP = "ingredient/alphabet"
    }

    @GET(INGREDIENT_GET_EP)
    suspend fun getIngredients(): ApiResponse<IngredientResponse>

    @GET(INGREDIENT_SEARCH_GET_EP)
    suspend fun searchIngredients(
        @Query("searchString") searchString: String
    ): ApiResponse<IngredientResponse>

    @GET(INGREDIENT_ALPHA_GET_EP)
    suspend fun getIngredientsByAlphabet(
        @Query("key") key: Char
    ): ApiResponse<IngredientResponse>
}
