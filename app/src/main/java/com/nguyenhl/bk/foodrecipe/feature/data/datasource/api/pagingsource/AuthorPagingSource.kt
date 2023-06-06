package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.pagingsource

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.ApiAuthor
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.AuthorResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.AuthorService
import com.skydoves.sandwich.getOrThrow
import retrofit2.HttpException
import java.io.IOException

class AuthorPagingSource(
    private val authorService: AuthorService
) : BasePagingSource<ApiAuthor, AuthorResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ApiAuthor> {
        val page = params.key ?: DEFAULT_PAGE_INDEX
        val response = authorService.getAuthors(page)
        return try {
            toLoadResult(
                response.getOrThrow(),
                page
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        } catch (exception: RuntimeException) {
            LoadResult.Error(exception)
        }
    }

    override fun toLoadResult(response: AuthorResponse, position: Int): LoadResult<Int, ApiAuthor> {
        val authors = response.authors
        val nextKey = if (authors.isEmpty() || position == response.pageCount) {
            null
        } else {
            position + 1
        }

        return LoadResult.Page(
            authors,
            if (position == DEFAULT_PAGE_INDEX) null else position - 1,
            nextKey
        )
    }

}
