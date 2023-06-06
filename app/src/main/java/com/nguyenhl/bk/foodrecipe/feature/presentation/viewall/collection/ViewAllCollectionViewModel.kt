package com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.collection

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.toCollectionDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.CollectionResponse
import com.nguyenhl.bk.foodrecipe.feature.data.repository.CollectionRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.CollectionDto
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.BaseViewAllViewModel
import kotlinx.coroutines.launch

class ViewAllCollectionViewModel constructor(
    val input: BaseInput.BaseViewAllInput.ViewAllCollectionInput,
    private val collectionRepository: CollectionRepository
) : BaseViewAllViewModel(input) {
    private val _collections: MutableLiveData<List<CollectionDto>> = MutableLiveData()
    val liveCollection: LiveData<List<CollectionDto>> = _collections

    init {
        viewModelScope.launch {
            fetchCollections()
        }
    }

    private suspend fun fetchCollections() {
        collectionRepository.fetchAllCollections().collect { response ->
            when (response) {
                is CollectionResponse -> {
                    _collections.postValue(response.collections.map { it.toCollectionDto() })
                }

                else -> {
                    _collections.postValue(listOf())
                }
            }
        }
    }
}
