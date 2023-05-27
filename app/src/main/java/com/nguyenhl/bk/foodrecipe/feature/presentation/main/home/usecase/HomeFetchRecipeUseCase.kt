package com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.usecase

import com.nguyenhl.bk.foodrecipe.feature.util.DispatchGroup
import kotlinx.coroutines.Dispatchers

class HomeFetchRecipeUseCase constructor(

) {
    private val dispatchGroup = DispatchGroup(Dispatchers.IO)

    suspend fun fetchRecipeData() {
        dispatchGroup.async {

        }

        dispatchGroup.awaitAll {

        }
    }
}
