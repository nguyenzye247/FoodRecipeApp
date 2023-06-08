package com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.chef

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.data.repository.AuthorRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.AuthorDto
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.BaseViewAllViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VAChefViewModel(
    val input: BaseInput.BaseViewAllInput.ViewAllTopChefInput,
    private val authorRepository: AuthorRepository
) : BaseViewAllViewModel(input) {

    private val _chefPaging: MutableStateFlow<PagingData<AuthorDto>?> =
        MutableStateFlow(null)
    fun getChefsPaging(): StateFlow<PagingData<AuthorDto>?> = _chefPaging

    init {
        viewModelScope.launch {
            fetchAuthors()
        }
    }

    private suspend fun fetchAuthors() {
        authorRepository.fetchAuthors()
            .collect {
                _chefPaging.value = it
            }
    }
}
