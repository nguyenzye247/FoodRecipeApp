package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.AuthorResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthorService {
    companion object {
        const val AUTHOR_GET_EP = "author"
    }

    @GET(AUTHOR_GET_EP)
    suspend fun getAuthors(
        @Query("page") page: Int
    ): ApiResponse<AuthorResponse>
}
