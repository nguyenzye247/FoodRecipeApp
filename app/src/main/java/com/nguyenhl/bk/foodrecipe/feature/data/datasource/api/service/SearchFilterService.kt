package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.searchfilter.*
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface SearchFilterService {
    companion object {
        const val FILTER_TIME_GET_EP = "filter/time"
        const val FILTER_AUTHOR_GET_EP = "filter/author"
        const val FILTER_INGREDIENT_GET_EP = "filter/ingredient"
        const val FILTER_SERVE_GET_EP = "filter/serve"
        const val FILTER_KCAL_GET_EP = "filter/kcal"
    }

    @GET(FILTER_TIME_GET_EP)
    suspend fun getTimeFilters(): ApiResponse<TimeFilterResponse>

    @GET(FILTER_AUTHOR_GET_EP)
    suspend fun getAuthorFilters(): ApiResponse<AuthorFilterResponse>

    @GET(FILTER_INGREDIENT_GET_EP)
    suspend fun getIngredientFilters(): ApiResponse<IngredientFilterResponse>

    @GET(FILTER_SERVE_GET_EP)
    suspend fun getServeFilters(): ApiResponse<ServeFilterResponse>

    @GET(FILTER_KCAL_GET_EP)
    suspend fun getKcalFilters(): ApiResponse<KcalFilterResponse>
}
