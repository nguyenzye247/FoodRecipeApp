package com.nguyenhl.bk.foodrecipe.feature.presentation.main.ingredients

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nguyenhl.bk.foodrecipe.feature.data.repository.IngredientRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.IngredientDto
import com.nguyenhl.bk.foodrecipe.feature.dto.alphabets
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class IngredientUseCase constructor(
    private val ingredientRepository: IngredientRepository
) {
    private val _alphabetKey: MutableLiveData<Char> = MutableLiveData(null)
    val liveAlphabetKey: LiveData<Char> = _alphabetKey
    fun setAlphabetKey(key: Char) {
        if (key == _alphabetKey.value) return
        _alphabetKey.value = key
    }

    private val _ingredients: MutableStateFlow<PagingData<IngredientDto>?> = MutableStateFlow(null)
    fun getIngredientsPaging(): StateFlow<PagingData<IngredientDto>?> = _ingredients

    private var loadIngredientPagingJob: Job? = null

    init {
        _alphabetKey.value = alphabets[0]
    }

    fun loadIngredientByAlphabet(key: Char, viewModelScope: CoroutineScope) {
        clearIngredientPagingJob()
        loadIngredientPagingJob = viewModelScope.launch(Dispatchers.IO) {
            fetchIngredientByAlphabet(key, this)
        }
    }

    private suspend fun fetchIngredientByAlphabet(key: Char, viewModelScope: CoroutineScope) {
        ingredientRepository.fetchIngredientsByAlphabet(key)
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
