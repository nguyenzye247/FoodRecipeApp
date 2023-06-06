package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.pagingsource

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.ApiIngredient
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.IngredientResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.IngredientService
import com.skydoves.sandwich.getOrThrow
import retrofit2.HttpException
import java.io.IOException

class IngredientPagingSource(
    private val ingredientEpType: IngredientEP,
    private val ingredientService: IngredientService,
    private val searchString: String = "",
    private val alphabetKey: Char = 'a'
): BasePagingSource<ApiIngredient, IngredientResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ApiIngredient> {
        val page = params.key ?: DEFAULT_PAGE_INDEX
        val response = when(ingredientEpType) {
            IngredientEP.ALL -> {
                ingredientService.getIngredients()
            }
            IngredientEP.SEARCH -> {
                ingredientService.searchIngredients(searchString)
            }
            IngredientEP.ALPHABET -> {
                ingredientService.getIngredientsByAlphabet(alphabetKey)
            }
        }

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

    override fun toLoadResult(
        response: IngredientResponse,
        position: Int
    ): LoadResult<Int, ApiIngredient> {
        val ingredients = response.ingredients
        val nextKey = if (ingredients.isEmpty() || position == response.pageCount) {
            null
        } else {
            position + 1
        }

        return LoadResult.Page(
            ingredients,
            if (position == DEFAULT_PAGE_INDEX) null else position - 1,
            nextKey
        )
    }
}

enum class IngredientEP{
    ALL,
    SEARCH,
    ALPHABET
}
