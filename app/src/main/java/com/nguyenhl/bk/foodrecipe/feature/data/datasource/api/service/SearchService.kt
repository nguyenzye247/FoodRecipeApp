package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.recipe.SearchRecipeFilterBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.recipe.RecipeResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface SearchService {
    companion object {
        const val RECIPE_SEARCH_POST_EP = "recipe/search"
    }

    @POST(RECIPE_SEARCH_POST_EP)
    suspend fun searchRecipeByFilters(
        @Header("Authorization") token: String,
        @Body searchFilterBody: SearchRecipeFilterBody,
        @Query("page") page: Int
    ): ApiResponse<RecipeResponse>
}
