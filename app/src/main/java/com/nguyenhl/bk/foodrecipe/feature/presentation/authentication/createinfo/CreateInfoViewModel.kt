package com.nguyenhl.bk.foodrecipe.feature.presentation.authentication.createinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.ErrorResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.HealthStatusResponse
import com.nguyenhl.bk.foodrecipe.feature.data.repository.HealthStatusRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CreateInfoViewModel constructor(
    val input: BaseInput.CreateInfoInput,
    private val healthStatusRepository: HealthStatusRepository
) : BaseViewModel(input) {
    private val _healthStatuses: MutableLiveData<HealthStatusResponse> = MutableLiveData()
    fun liveHealthStatuses(): LiveData<HealthStatusResponse> = _healthStatuses

    fun getAllHealthStatuses() {
        viewModelScope.launch {
            healthStatusRepository.getAllHealthStatus().collectLatest {
                when (it) {
                    is HealthStatusResponse -> {
                        _healthStatuses.postValue(it)
                    }

                    is ErrorResponse -> {

                    }

                    else -> {

                    }
                }
            }
        }
    }

}
