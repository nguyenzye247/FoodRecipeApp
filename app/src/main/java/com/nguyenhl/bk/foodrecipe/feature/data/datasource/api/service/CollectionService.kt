package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.CollectionResponse
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET

interface CollectionService {
    companion object {
        const val COLLECTION_GET_EP = "collection"
    }

    @GET(COLLECTION_GET_EP)
    suspend fun getAllCollections(): ApiResponse<CollectionResponse>
}
