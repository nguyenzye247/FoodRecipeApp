package com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.ingredient

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.data.repository.IngredientRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.IngredientDto
import com.nguyenhl.bk.foodrecipe.feature.dto.alphabets
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.BaseViewAllViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class VAIngredientViewModel(
    val input: BaseInput.BaseViewAllInput.ViewAllIngredientInput,
    private val ingredientRepository: IngredientRepository
) : BaseViewAllViewModel(input) {
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

    fun loadIngredientByAlphabet(key: Char) {
        clearIngredientPagingJob()
        loadIngredientPagingJob = viewModelScope.launch(Dispatchers.IO) {
            fetchIngredientByAlphabet(key)
        }
    }

    private suspend fun fetchIngredientByAlphabet(key: Char) {
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
