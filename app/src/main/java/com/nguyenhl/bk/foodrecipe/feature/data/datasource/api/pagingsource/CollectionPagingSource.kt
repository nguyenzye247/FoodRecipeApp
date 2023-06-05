package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.pagingsource

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.ApiCollection
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.CollectionResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.CollectionService
import com.skydoves.sandwich.getOrThrow
import retrofit2.HttpException
import java.io.IOException

class CollectionPagingSource(
    private val collectionService: CollectionService
) : BasePagingSource<ApiCollection, CollectionResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ApiCollection> {
        val page = params.key ?: DEFAULT_PAGE_INDEX
        val response = collectionService.getAllCollections()
        return try {
            toLoadResult(
                response.getOrThrow(),
                page
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun toLoadResult(
        response: CollectionResponse,
        position: Int
    ): LoadResult<Int, ApiCollection> {
        val collections = response.collections
        val nextKey = if (collections.isEmpty()) {
            null
        } else {
            position + 1
        }

        return LoadResult.Page(
            collections,
            if (position == DEFAULT_PAGE_INDEX) null else position - 1,
            nextKey
        )
    }
}