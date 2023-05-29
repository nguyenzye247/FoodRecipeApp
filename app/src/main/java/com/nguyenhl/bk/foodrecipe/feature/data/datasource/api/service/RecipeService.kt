package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.recipe.SearchRecipeFilterBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.recipe.SearchRecipeResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface RecipeService {
    companion object {
        const val RECIPE_SEARCH_POST_EP = "recipe/search"
    }

    @POST(RECIPE_SEARCH_POST_EP)
    suspend fun searchRecipeByFilters(
        @Body searchFilterBody: SearchRecipeFilterBody,
        @Query("page") page: Int
    ): ApiResponse<SearchRecipeResponse>
}
