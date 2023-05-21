package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.DishPreferredResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface DishPreferredService {
    companion object {
        const val DISH_PREFERRED_GET_EP = "likedish"
    }

    @GET(DISH_PREFERRED_GET_EP)
    suspend fun getAllPreferredDishes(): ApiResponse<DishPreferredResponse>
}
