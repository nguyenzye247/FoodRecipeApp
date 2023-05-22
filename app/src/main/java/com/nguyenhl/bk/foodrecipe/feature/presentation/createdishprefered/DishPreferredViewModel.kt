package com.nguyenhl.bk.foodrecipe.feature.presentation.createdishprefered

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.userinfo.toDishPreferredDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.DishPreferredResponse
import com.nguyenhl.bk.foodrecipe.feature.data.repository.DishPreferredRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.DishPreferredDto
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DishPreferredViewModel constructor(
    val input: BaseInput.DishPreferredInput,
    private val dishPreferredRepository: DishPreferredRepository
) : BaseViewModel(input) {
    private val _preferredDishes: MutableLiveData<List<DishPreferredDto>?> = MutableLiveData()
    fun livePreferredDishes(): LiveData<List<DishPreferredDto>?> = _preferredDishes

    private val _hasDishSelected: MutableLiveData<Boolean> = MutableLiveData(false)
    fun liveHasDishSelected(): LiveData<Boolean> = _hasDishSelected
    fun hasDishSelectedValue(): Boolean = _hasDishSelected.value == true
    fun hasDishSelected(hasSelected: Boolean) {
        _hasDishSelected.value = hasSelected
    }

    init {
        setLoading(true)
        getAllPreferredDishes()
    }

    private fun getAllPreferredDishes() {
        viewModelScope.launch {
            dishPreferredRepository.getAllPreferredDishes().collectLatest { response ->
                when (response) {
                    is DishPreferredResponse -> {
                        _preferredDishes.postValue(response.data.map { it.toDishPreferredDto() })
                    }
                    else -> {
                        _preferredDishes.postValue(null)
                    }
                }
            }
        }
    }
}
