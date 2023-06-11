package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.IngredientDetailResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.IngredientResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IngredientService {
    companion object {
        const val INGREDIENT_GET_EP = "ingredient"
        const val INGREDIENT_SEARCH_GET_EP = "ingredient/search"
        const val INGREDIENT_ALPHA_GET_EP = "ingredient/alphabet"
        const val INGREDIENT_DETAIL_GET_EP = "ingredientdetail"
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

    @GET("$INGREDIENT_DETAIL_GET_EP/{detailId}")
    suspend fun getIngredientDetail(
        @Path("detailId") detailId: String
    ): ApiResponse<IngredientDetailResponse>
}
