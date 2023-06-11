package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.ingredient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.toIngredientDetailDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.IngredientDetailResponse
import com.nguyenhl.bk.foodrecipe.feature.data.repository.IngredientRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.IngredientDetailDto
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class IngredientDetailViewModel(
    val input: BaseInput.IngredientDetailInput,
    private val ingredientRepository: IngredientRepository
) : BaseViewModel(input) {
    private val ingredientInfo by lazy { input.ingredientDto }


    private val _ingredientDetail: MutableLiveData<IngredientDetailDto?> =
        MutableLiveData(null)
    fun getIngredientDetail(): LiveData<IngredientDetailDto?> = _ingredientDetail

    init {
        viewModelScope.launch {
            fetchIngredientDetail()
        }
    }

    suspend fun fetchIngredientDetail() {
        ingredientInfo?.let { ingredient ->
            ingredientRepository.fetchIngredientDetail(ingredient.idIngredientDetail)
                .collectLatest { response ->
                    when (response) {
                        is IngredientDetailResponse -> {
                            _ingredientDetail.value = response.detail.toIngredientDetailDto()
                        }

                        else -> {
                            _ingredientDetail.value = null
                        }
                    }
                }
        }
    }
}