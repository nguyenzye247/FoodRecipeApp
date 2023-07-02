package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.weeklyplan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.weeklyplan.toWeeklyPlanDetailDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.weeklyplan.WeeklyPlanDetailResponse
import com.nguyenhl.bk.foodrecipe.feature.data.repository.WeeklyPlanRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.weeklyplan.detail.WeeklyPlanDetailDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WeeklyPlanDetailViewModel constructor(
    val input: BaseInput.WeeklyPlanDetailInput,
    private val weeklyPlanRepository: WeeklyPlanRepository
) : BaseViewModel(input) {

    private val _weeklyPlanDetail: MutableLiveData<WeeklyPlanDetailDto?> = MutableLiveData()
    fun liveWeeklyPlanDetail(): LiveData<WeeklyPlanDetailDto?> = _weeklyPlanDetail

    init {
        fetchWeeklyPlanDetail()
    }

    private fun fetchWeeklyPlanDetail() {
        val weeklyPlanDetailId = input.weeklyPlanDto?.idWeeklyPlanDetail ?: return
        viewModelScope.launch(Dispatchers.IO) {
            weeklyPlanRepository.fetchWeeklyPlanDetails(weeklyPlanDetailId)
                .collectLatest { response ->
                    when (response) {
                        is WeeklyPlanDetailResponse -> {
                            _weeklyPlanDetail.postValue(response.weeklyPlanDetails.toWeeklyPlanDetailDto())
                        }

                        else -> {
                            _weeklyPlanDetail.postValue(null)
                        }
                    }
                }
        }
    }
}
