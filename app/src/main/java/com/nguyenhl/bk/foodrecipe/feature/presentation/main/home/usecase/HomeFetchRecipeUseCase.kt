package com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nguyenhl.bk.foodrecipe.core.extension.collection.getRandomSubset
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.recipe.SearchRecipeFilterBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.ApiCollection
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.recipe.toRecipeDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.toCollection
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.toCollectionDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.CollectionResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.ErrorResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.recipe.SearchRecipeResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.CategoryDetail
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.toDishPreferredDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.toUserInfoDto
import com.nguyenhl.bk.foodrecipe.feature.data.repository.CollectionRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.DishPreferredRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.RecipeRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.UserInfoRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.CollectionDto
import com.nguyenhl.bk.foodrecipe.feature.dto.DishPreferredDto
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto
import com.nguyenhl.bk.foodrecipe.feature.dto.UserInfoDto
import com.nguyenhl.bk.foodrecipe.feature.util.DispatchGroup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest

class HomeFetchRecipeUseCase constructor(
    private val dishPreferredRepository: DishPreferredRepository,
    private val userInfoRepository: UserInfoRepository,
    private val recipeRepository: RecipeRepository,
    private val collectionRepository: CollectionRepository
) {
    private val dispatchGroup = DispatchGroup(Dispatchers.IO)

    private val _userInfo: MutableLiveData<UserInfoDto> = MutableLiveData()
    fun liveUserInfo(): LiveData<UserInfoDto> = _userInfo

    private val _preferredDishes: MutableLiveData<List<DishPreferredDto>> = MutableLiveData()
    fun livePreferredDishes(): LiveData<List<DishPreferredDto>> = _preferredDishes

    private val _suggestRecipes: MutableLiveData<List<RecipeDto>> = MutableLiveData()
    fun liveSuggestRecipes(): LiveData<List<RecipeDto>> = _suggestRecipes

    private val _collections: MutableLiveData<List<CollectionDto>> = MutableLiveData()
    fun liveCollections(): LiveData<List<CollectionDto>> = _collections



    suspend fun fetchRecipeData(userId: String, onFetchFinished: () -> Unit) {
        val userInfo = userInfoRepository.getUserInfoByUserId(userId) ?: return
        _userInfo.postValue(userInfo.toUserInfoDto())

        dispatchGroup.apply {
            async {
                fetchPreferredDishes(userInfo.user.userId)
            }
            async {
                fetchSuggestRecipes(userInfo.healthStatusWithCategoryDetail.categoryDetails)
            }
            async {
                fetchCollections()
            }
        }

        dispatchGroup.awaitAll {
            onFetchFinished.invoke()
        }
    }

    private suspend fun fetchPreferredDishes(userId: String) {
        val preferredDishes = dishPreferredRepository.getAllPreferredDishByUserId(userId)
        _preferredDishes.value = preferredDishes.map { it.toDishPreferredDto() }
    }

    private suspend fun fetchSuggestRecipes(categoryDetails: List<CategoryDetail>) {
        val searchRecipeFilterBody = SearchRecipeFilterBody(
            idCategoryDetails = categoryDetails.map { it.idCategoryDetail },
            null,
            null,
            null,
            null
        )
        recipeRepository.searchRecipeByFilters(searchRecipeFilterBody).collectLatest { response ->
            when (response) {
                is SearchRecipeResponse -> {
                    _suggestRecipes.postValue(response.recipes.map { it.toRecipeDto() })
                }

                else -> {
                    _suggestRecipes.postValue(listOf())
                }
            }
        }
    }

    private suspend fun fetchCollections() {
        collectionRepository.fetchAllCollections().collectLatest { response ->
            when (response) {
                is CollectionResponse -> {
                    val top10Collections = response.collections
                        .getRandomSubset(ITEM_SIZE)
                        .map { it.toCollectionDto() }
                    _collections.postValue(top10Collections)

                    saveCollections(response.collections)
                }

                else -> {
                    _collections.postValue(emptyList())
                }
            }
        }
    }

    private suspend fun saveCollections(apiCollections: List<ApiCollection>) {
        val collections = apiCollections.map { it.toCollection() }
        collectionRepository.saveCollections(collections)
    }

    companion object {
        private const val ITEM_SIZE = 10
    }
}
