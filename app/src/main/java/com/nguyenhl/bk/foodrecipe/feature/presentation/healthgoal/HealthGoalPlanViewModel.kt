package com.nguyenhl.bk.foodrecipe.feature.presentation.healthgoal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.healthgoal.toHealthGoalDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.healthgoal.toPhysicalLevelDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.healthgoal.HealthGoalResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.healthgoal.PhysicalLevelResponse
import com.nguyenhl.bk.foodrecipe.feature.data.repository.HealthGoalRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.healthgoal.HealthGoalDto
import com.nguyenhl.bk.foodrecipe.feature.dto.healthgoal.PhysicalLevelDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HealthGoalPlanViewModel(
    val input: BaseInput.HealthGoalPlanInput,
    private val healthGoalRepository: HealthGoalRepository
) : BaseViewModel(input) {
    private val _physicalLevels: MutableLiveData<List<PhysicalLevelDto>?> = MutableLiveData()
    val livePhysicalLevels: LiveData<List<PhysicalLevelDto>?> = _physicalLevels

    private val _healthGoals: MutableLiveData<List<HealthGoalDto>?> = MutableLiveData()
    val liveHealthGoals: LiveData<List<HealthGoalDto>?> = _healthGoals

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchAllPhysicalLevels()
            fetchAllHealthGoals()
        }
    }

    private suspend fun fetchAllPhysicalLevels() {
        healthGoalRepository.fetchAllPhysicalLevels().collectLatest { response ->
            when (response) {
                is PhysicalLevelResponse -> {
                    _physicalLevels.postValue(response.physicalLevels.map { it.toPhysicalLevelDto() })
                }

                else -> {
                    _physicalLevels.postValue(emptyList())
                }
            }
        }
    }

    private suspend fun fetchAllHealthGoals() {
        healthGoalRepository.fetchAllHealthGoals().collectLatest { response ->
            when (response) {
                is HealthGoalResponse -> {
                    _healthGoals.postValue(response.healthGoals.map { it.toHealthGoalDto() })
                }

                else -> {
                    _healthGoals.postValue(emptyList())
                }
            }
        }
    }
}
