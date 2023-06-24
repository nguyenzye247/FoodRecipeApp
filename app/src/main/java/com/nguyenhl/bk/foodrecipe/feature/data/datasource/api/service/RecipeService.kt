package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.recipe.RecipeByAuthorBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.recipe.LikeRecipeResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.recipe.RecipeDetailResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.recipe.RecipeResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.*

interface RecipeService {
    companion object {
        const val RECIPE_RANDOM_GET_EP = "recipe/random"
        const val RECIPE_BY_COLLECTION_GET_EP = "recipe/collection"
        const val RECIPE_BY_INGREDIENT_GET_EP = "recipe/ingredient"
        const val RECIPE_BY_AUTHOR_POST_EP = "recipe/author"

        const val RECIPE_DETAIL_GET_EP = "recipedetail"

        const val RECIPE_LIKE_POST_EP = "like"
        const val RECIPE_LIKE_GET_EP = "like"
    }

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

    @GET("$RECIPE_DETAIL_GET_EP/{detailId}")
    suspend fun getRecipeDetails(
        @Path("detailId") detailId: String
    ): ApiResponse<RecipeDetailResponse>

    @POST("$RECIPE_LIKE_POST_EP/{recipeId}")
    suspend fun likeRecipe(
        @Header("Authorization") token: String,
        @Path("recipeId") recipeId: String
    ): ApiResponse<LikeRecipeResponse>

    @GET(RECIPE_LIKE_GET_EP)
    suspend fun getAllLikedRecipe(
        @Header("Authorization") token: String
    ): ApiResponse<RecipeResponse>
}
