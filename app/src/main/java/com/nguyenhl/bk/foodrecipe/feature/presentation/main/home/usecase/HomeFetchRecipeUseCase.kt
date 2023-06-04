package com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.usecase

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nguyenhl.bk.foodrecipe.core.extension.collection.getRandomSubset
import com.nguyenhl.bk.foodrecipe.core.extension.toast
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.recipe.SearchRecipeFilterBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.*
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.recipe.toRecipeDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.AuthorResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.CollectionResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.IngredientResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.recipe.RecipeResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.CategoryDetail
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.toDishPreferredDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.toUserInfoDto
import com.nguyenhl.bk.foodrecipe.feature.data.repository.*
import com.nguyenhl.bk.foodrecipe.feature.dto.*
import com.nguyenhl.bk.foodrecipe.feature.helper.SessionManager
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import timber.log.Timber

class HomeFetchRecipeUseCase constructor(
    private val dishPreferredRepository: DishPreferredRepository,
    private val userInfoRepository: UserInfoRepository,
    private val recipeRepository: RecipeRepository,
    private val collectionRepository: CollectionRepository,
    private val authorRepository: AuthorRepository,
    private val ingredientRepository: IngredientRepository,
) {

    private val _userInfo: MutableLiveData<UserInfoDto?> = MutableLiveData()
    fun liveUserInfo(): LiveData<UserInfoDto?> = _userInfo

    private val _preferredDishes: MutableLiveData<List<DishPreferredDto>?> = MutableLiveData()
    fun livePreferredDishes(): LiveData<List<DishPreferredDto>?> = _preferredDishes

    private val _suggestRecipes: MutableLiveData<List<RecipeDto>?> = MutableLiveData()
    fun liveSuggestRecipes(): LiveData<List<RecipeDto>?> = _suggestRecipes

    private val _randomRecipes: MutableLiveData<List<RecipeDto>?> = MutableLiveData()
    fun liveRandomRecipes(): LiveData<List<RecipeDto>?> = _randomRecipes

    private val _collections: MutableLiveData<List<CollectionDto>?> = MutableLiveData()
    fun liveCollections(): LiveData<List<CollectionDto>?> = _collections

    private val _authors: MutableLiveData<List<AuthorDto>?> = MutableLiveData()
    fun liveAuthors(): LiveData<List<AuthorDto>?> = _authors

    private val _ingredients: MutableLiveData<List<IngredientDto>?> = MutableLiveData()
    fun liveIngredients(): LiveData<List<IngredientDto>?> = _ingredients

    suspend fun fetchRecipeData(context: Context, userId: String, onFetchFinished: () -> Unit) {
        val userInfo = userInfoRepository.getUserInfoByUserId(userId) ?: return
        _userInfo.postValue(userInfo.toUserInfoDto())

        coroutineScope {
            val deferredResults = listOf(
                async { fetchPreferredDishes(userInfo.user.userId) },
                async { fetchSuggestRecipes(context, userInfo.healthStatusWithCategoryDetail.categoryDetails) },
                async { fetchCollections() },
                async { fetchAuthors() },
                async { fetchRandomRecipes(context) },
                async { fetchIngredients() }
            )

            deferredResults.awaitAll()
            Timber.tag(TAG).d("FINISH")
            onFetchFinished()
        }
    }

    private suspend fun fetchPreferredDishes(userId: String) {
        val preferredDishes = dishPreferredRepository.getAllPreferredDishByUserId(userId)
        _preferredDishes.postValue(preferredDishes.map { it.toDishPreferredDto() })
        Timber.tag(TAG).d("PreferredDishes: ${preferredDishes.size}")
    }

    private suspend fun fetchSuggestRecipes(
        context: Context,
        categoryDetails: List<CategoryDetail>
    ) {
        val token = SessionManager.fetchToken(context).ifEmpty {
            context.toast("Empty token")
            return
        }
        val searchRecipeFilterBody = SearchRecipeFilterBody(
            idCategoryDetails = categoryDetails.map { it.idCategoryDetail },
            null,
            null,
            null,
            null
        )
        recipeRepository.searchRecipeByFilters(token, searchRecipeFilterBody).collect { response ->
            when (response) {
                is RecipeResponse -> {
                    _suggestRecipes.postValue(response.recipes.map { it.toRecipeDto() })
                    Timber.tag(TAG).d("SuggestRecipes: ${response.recipes.size}")
                }

                else -> {
                    _suggestRecipes.postValue(null)
                }
            }
        }
    }

    private suspend fun fetchCollections() {
        collectionRepository.fetchAllCollections().collect { response ->
            when (response) {
                is CollectionResponse -> {
                    val top10Collections = response.collections
                        .getRandomSubset(ITEM_SIZE)
                        .map { it.toCollectionDto() }
                    _collections.postValue(top10Collections)
                    Timber.tag(TAG).d("Collections: ${response.collections.size}")

                    saveCollections(response.collections)
                }

                else -> {
                    _collections.postValue(null)
                }
            }
        }
    }

    private suspend fun saveCollections(apiCollections: List<ApiCollection>) {
        val collections = apiCollections.map { it.toCollection() }
        collectionRepository.saveCollections(collections)
    }

    private suspend fun fetchAuthors() {
        authorRepository.fetchAuthors().collect { response ->
            when (response) {
                is AuthorResponse -> {
                    _authors.postValue(response.authors.map { it.toAuthorDto() })
                    Timber.tag(TAG).d("Authors: ${response.authors.size}")
                }

                else -> {
                    _authors.postValue(null)
                }
            }
        }
    }

    private suspend fun fetchRandomRecipes(context: Context) {
        val token = SessionManager.fetchToken(context).ifEmpty {
            context.toast("Empty token")
            return
        }
        recipeRepository.fetchRandomRecipes(token).collect { response ->
            when (response) {
                is RecipeResponse -> {
                    _randomRecipes.postValue(response.recipes.map { it.toRecipeDto() })
                    Timber.tag(TAG).d("RandomRecipes: ${response.recipes.size}")
                }

                else -> {
                    _randomRecipes.postValue(null)
                }
            }
        }
    }

    private suspend fun fetchIngredients() {
        ingredientRepository.fetchIngredients().collect { response ->
            when (response) {
                is IngredientResponse -> {
                    _ingredients.postValue(response.ingredients.map { it.toIngredientDto() })
                    Timber.tag(TAG).d("Ingredients: ${response.ingredients.size}")
                }

                else -> {
                    _ingredients.postValue(null)
                }
            }
        }
    }

    companion object {
        private const val ITEM_SIZE = 10
        private const val TAG = "HOME_TAG"
    }
}
