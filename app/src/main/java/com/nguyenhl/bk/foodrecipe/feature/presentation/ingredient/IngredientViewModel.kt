package com.nguyenhl.bk.foodrecipe.feature.presentation.ingredient

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.repository.IngredientRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.IngredientDto
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.ingredients.IngredientUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class IngredientViewModel constructor(
    val input: BaseInput.IngredientInput,
    private val ingredientRepository: IngredientRepository
): BaseViewModel(input) {

    private val _ingredients: MutableStateFlow<PagingData<IngredientDto>?> = MutableStateFlow(null)
    fun getIngredientsPaging(): StateFlow<PagingData<IngredientDto>?> = _ingredients

    private var loadIngredientPagingJob: Job? = null

    init {
        searchIngredient("a")
    }

    fun searchIngredient(query: String) {
        clearIngredientPagingJob()
        loadIngredientPagingJob = viewModelScope.launch(Dispatchers.IO) {
            fetchIngredientByQuery(query, this)
        }
    }

    private suspend fun fetchIngredientByQuery(query: String, viewModelScope: CoroutineScope) {
        ingredientRepository.searchIngredients(query)
            .cachedIn(viewModelScope)
            .collect {
                _ingredients.value = it
            }
    }

    private fun clearIngredientPagingJob() {
        if (loadIngredientPagingJob != null) {
            loadIngredientPagingJob?.cancel()
            loadIngredientPagingJob = null
            _ingredients.value = null
        }
    }
}
