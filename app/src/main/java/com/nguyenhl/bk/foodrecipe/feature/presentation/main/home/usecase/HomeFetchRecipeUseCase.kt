package com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.CategoryDetail
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.PreferredDish
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.toDishPreferredDto
import com.nguyenhl.bk.foodrecipe.feature.data.repository.DishPreferredRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.UserInfoRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.UserRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.DishPreferredDto
import com.nguyenhl.bk.foodrecipe.feature.util.DispatchGroup
import kotlinx.coroutines.Dispatchers

class HomeFetchRecipeUseCase constructor(
    private val dishPreferredRepository: DishPreferredRepository,
    private val userInfoRepository: UserInfoRepository
) {
    private val dispatchGroup = DispatchGroup(Dispatchers.IO)

    private val _preferredDishes: MutableLiveData<List<DishPreferredDto>> = MutableLiveData()
    fun livePreferredDishes(): LiveData<List<DishPreferredDto>> = _preferredDishes

    suspend fun fetchRecipeData(userId: String, onFetchFinished: () -> Unit) {
        val userInfo = userInfoRepository.getUserInfoByUserId(userId) ?: return

        dispatchGroup.apply {
            async {
                fetchPreferredDishes(userInfo.user.userId)
            }
            async {
                fetchSuggestRecipes(userInfo.healthStatusWithCategoryDetail.categoryDetails)
            }
            async {

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

    }
}
