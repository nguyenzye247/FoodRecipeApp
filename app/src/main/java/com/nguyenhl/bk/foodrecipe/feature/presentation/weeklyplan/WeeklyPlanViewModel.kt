package com.nguyenhl.bk.foodrecipe.feature.presentation.weeklyplan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.weeklyplan.toWeeklyPlanDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.weeklyplan.WeeklyPlanResponse
import com.nguyenhl.bk.foodrecipe.feature.data.repository.WeeklyPlanRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.weeklyplan.WeeklyPlanDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WeeklyPlanViewModel constructor(
    val input: BaseInput.WeeklyPlanInput,
    private val weeklyPlanRepository: WeeklyPlanRepository
) : BaseViewModel(input) {

    private val _weeklyPlans: MutableLiveData<List<WeeklyPlanDto>?> = MutableLiveData()
    fun liveWeeklyPlan(): LiveData<List<WeeklyPlanDto>?> = _weeklyPlans

    init {
        setLoading(true)
        fetchAllWeeklyPlans()
    }

    private fun fetchAllWeeklyPlans() {
        viewModelScope.launch(Dispatchers.IO) {
            weeklyPlanRepository.fetchAllWeeklyPlans().collectLatest { response ->
                setLoading(false)
                when (response) {
                    is WeeklyPlanResponse -> {
                        _weeklyPlans.postValue(response.weeklyPlans.map { it.toWeeklyPlanDto() })
                    }

                    else -> {
                        _weeklyPlans.postValue(null)
                    }
                }
            }
        }
    }
}
