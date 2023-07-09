package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.healthgoal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nguyenhl.bk.foodrecipe.core.extension.toast
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.healthgoal.toHealthGoalDetailDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.healthgoal.HealthGoalDetailResponse
import com.nguyenhl.bk.foodrecipe.feature.data.repository.HealthGoalRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.HealthIndicatorRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.healthgoal.HealthGoalDetailDto
import com.nguyenhl.bk.foodrecipe.feature.helper.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HealthGoalPlanDetailViewModel constructor(
    val input: BaseInput.HealthGoalPlanDetailInput,
    private val healthGoalRepository: HealthGoalRepository,
    private val healthIndicatorRepository: HealthIndicatorRepository
): BaseViewModel(input) {

    private val _healthGoalDetail: MutableLiveData<HealthGoalDetailDto?> = MutableLiveData()
    val liveHealthGoalDetail: LiveData<HealthGoalDetailDto?> = _healthGoalDetail

    init {
        val healthGoal = input.healthGoalPlanDto
        healthGoal?.let {
            fetchHealthGoalDetails(it.apiId)
        }
    }

    private fun fetchHealthGoalDetails(healthGoalId: String) {
        val token = SessionManager.fetchToken(input.application).ifEmpty {
            input.application.toast("Empty token")
            return
        }
        setLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            healthGoalRepository.fetchHealthGoalDetail(token, healthGoalId).collectLatest { response ->
                setLoading(false)
                when (response) {
                    is HealthGoalDetailResponse -> {
                        _healthGoalDetail.postValue(response.healthGoalDetail.toHealthGoalDetailDto())
                    }

                    else -> {
                        _healthGoalDetail.postValue(null)
                    }
                }
            }
        }
    }
}
