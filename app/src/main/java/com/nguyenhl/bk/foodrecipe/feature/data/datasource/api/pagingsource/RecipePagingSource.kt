package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.pagingsource

import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.recipe.RecipeByAuthorBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.recipe.toRecipeDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.recipe.RecipeResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.service.RecipeService
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto
import com.skydoves.sandwich.getOrThrow
import retrofit2.HttpException
import java.io.IOException

class RecipePagingSource(
    private val recipeEPType: RecipeEP,
    private val token: String,
    private val recipeService: RecipeService,
    private val idParent: String = ""
) : BasePagingSource<RecipeDto, RecipeResponse>()  {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecipeDto> {
        val page = params.key ?: DEFAULT_PAGE_INDEX
        val response = when (recipeEPType) {
            RecipeEP.BY_CHEF -> {
                recipeService.getRecipesByAuthor(token, RecipeByAuthorBody(idParent), page)
            }
            RecipeEP.RANDOM -> {
                recipeService.getRandomRecipes(token, page)
            }
            RecipeEP.BY_COLLECTION -> {
                recipeService.getRecipesByCollection(token, idParent, page)
            }
            RecipeEP.BY_INGREDIENT -> {
                recipeService.getRecipesByIngredient(token, idParent, page)
            }
            RecipeEP.LIKED -> {
                recipeService.getAllLikedRecipe(token)
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

    override fun toLoadResult(response: RecipeResponse, position: Int): LoadResult<Int, RecipeDto> {
        val recipes = response.recipes
        val nextKey = if (recipes.isEmpty() || position == response.pageCount) {
            null
        } else {
            position + 1
        }

        return LoadResult.Page(
            recipes.map { it.toRecipeDto() },
            if (position == DEFAULT_PAGE_INDEX) null else position - 1,
            nextKey
        )
    }
}

enum class RecipeEP {
    RANDOM,
    BY_COLLECTION,
    BY_INGREDIENT,
    BY_CHEF,
    LIKED
}
