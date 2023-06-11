package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.recipe.RecipeByAuthorBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.recipe.SearchRecipeFilterBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.recipe.RecipeResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface RecipeService {
    companion object {
        const val RECIPE_SEARCH_POST_EP = "recipe/search"
        const val RECIPE_RANDOM_GET_EP = "recipe/random"
        const val RECIPE_BY_COLLECTION_GET_EP = "recipe/collection"
        const val RECIPE_BY_INGREDIENT_GET_EP = "recipe/ingredient"
        const val RECIPE_BY_AUTHOR_POST_EP = "recipe/author"
    }

    @POST(RECIPE_SEARCH_POST_EP)
    suspend fun searchRecipeByFilters(
        @Header("Authorization") token: String,
        @Body searchFilterBody: SearchRecipeFilterBody,
        @Query("page") page: Int
    ): ApiResponse<RecipeResponse>

    @GET(RECIPE_RANDOM_GET_EP)
    suspend fun getRandomRecipes(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1
    ): ApiResponse<RecipeResponse>

    @GET(RECIPE_BY_COLLECTION_GET_EP)
    suspend fun getRecipesByCollection(
        @Header("Authorization") token: String,
        @Query("id") idCollection: String,
        @Query("page") page: Int = 1
    ): ApiResponse<RecipeResponse>

    @GET(RECIPE_BY_INGREDIENT_GET_EP)
    suspend fun getRecipesByIngredient(
        @Header("Authorization") token: String,
        @Query("id") idIngredient: String,
        @Query("page") page: Int = 1
    ): ApiResponse<RecipeResponse>

    @POST(RECIPE_BY_AUTHOR_POST_EP)
    suspend fun getRecipesByAuthor(
        @Header("Authorization") token: String,
        @Body recipeByAuthorBody: RecipeByAuthorBody,
        @Query("page") page: Int = 1
    ): ApiResponse<RecipeResponse>
}
