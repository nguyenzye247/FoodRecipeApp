package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.calendar.RecipeByDateBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.calendar.RecipeDateBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.calendar.AddRecipeToDateResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.calendar.DateHaveRecipeResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.calendar.DeleteRecipeFromDateResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.calendar.RecipeByDateResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.*

interface CalendarService {
    companion object {
        const val RECIPE_CALENDAR_POST_EP = "calendar/create"
        const val RECIPE_CALENDAR_DELETE_EP = "calendar/delete"
        const val RECIPE_CALENDAR_GET_EP = "calendar/day"
        const val ALL_RECIPE_CALENDAR_GET_EP = "calendar/day"
    }

    @POST(RECIPE_CALENDAR_POST_EP)
    suspend fun addRecipeToDate(
        @Header("Authorization") token: String,
        @Body recipeDateBody: RecipeDateBody
    ): ApiResponse<AddRecipeToDateResponse>

    @DELETE("$RECIPE_CALENDAR_DELETE_EP/{recipeByDateId}")
    suspend fun removeRecipeFromDate(
        @Header("Authorization") token: String,
        @Path("recipeByDateId") recipeByDateId: String
    ): ApiResponse<DeleteRecipeFromDateResponse>

    @POST(RECIPE_CALENDAR_GET_EP)
    suspend fun getRecipeByDate(
        @Header("Authorization") token: String,
        @Body recipeByDateBody: RecipeByDateBody
    ): ApiResponse<RecipeByDateResponse>

    @GET(ALL_RECIPE_CALENDAR_GET_EP)
    suspend fun getAllDateHaveRecipe(
        @Header("Authorization") token: String
    ): ApiResponse<DateHaveRecipeResponse>
}
