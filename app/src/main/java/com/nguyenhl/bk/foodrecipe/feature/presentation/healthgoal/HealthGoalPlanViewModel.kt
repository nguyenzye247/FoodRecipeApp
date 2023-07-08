package com.nguyenhl.bk.foodrecipe.feature.presentation.healthgoal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nguyenhl.bk.foodrecipe.core.extension.toast
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.healthgoal.CreateHealthGoalBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.healthgoal.toHealthGoalDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.healthgoal.toPhysicalLevelDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.ErrorResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.healthgoal.CreateHealthGoalResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.healthgoal.HealthGoalResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.healthgoal.PhysicalLevelResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.healthgoal.toApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.toApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.data.repository.HealthGoalRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.ApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.dto.healthgoal.HealthGoalDto
import com.nguyenhl.bk.foodrecipe.feature.dto.healthgoal.PhysicalLevelDto
import com.nguyenhl.bk.foodrecipe.feature.helper.SessionManager
import com.nguyenhl.bk.foodrecipe.feature.util.DateFormatUtil.formatSimpleDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

class HealthGoalPlanViewModel(
    val input: BaseInput.HealthGoalPlanInput,
    private val healthGoalRepository: HealthGoalRepository
) : BaseViewModel(input) {
    private val _physicalLevels: MutableLiveData<List<PhysicalLevelDto>?> = MutableLiveData()
    val livePhysicalLevels: LiveData<List<PhysicalLevelDto>?> = _physicalLevels

    val physicalLevels: ArrayList<PhysicalLevelDto> = arrayListOf()
    var selectedPhysicalLevel: PhysicalLevelDto? = physicalLevels.firstOrNull()

    private val _healthGoals: MutableLiveData<List<HealthGoalDto>?> = MutableLiveData()
    val liveHealthGoals: LiveData<List<HealthGoalDto>?> = _healthGoals

    private val _createHealthGoal: MutableLiveData<ApiCommonResponse?> = MutableLiveData()
    val liveCreateHealthGoal: LiveData<ApiCommonResponse?> = _createHealthGoal

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchAllPhysicalLevels()
        }
        fetchAllHealthGoals()
    }

    private suspend fun fetchAllPhysicalLevels() {
        healthGoalRepository.fetchAllPhysicalLevels().collectLatest { response ->
            when (response) {
                is PhysicalLevelResponse -> {
                    val pLevels = response.physicalLevels.map { it.toPhysicalLevelDto() }
                    physicalLevels.clear()
                    physicalLevels.addAll(pLevels)
                    _physicalLevels.postValue(pLevels)
                }

                else -> {
                    _physicalLevels.postValue(emptyList())
                }
            }
        }
    }

    fun fetchAllHealthGoals() {
        val token = SessionManager.fetchToken(input.application).ifEmpty {
            input.application.toast("Empty token")
            return
        }
        setLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            healthGoalRepository.fetchAllHealthGoals(token).collectLatest { response ->
                setLoading(false)
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

    fun createHealthGoal(targetWeight: Float, dayGoal: Int, physicalLevelId: String) {
        val token = SessionManager.fetchToken(input.application).ifEmpty {
            input.application.toast("Empty token")
            return
        }
        setLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            healthGoalRepository.createHealthGoal(
                token,
                CreateHealthGoalBody(
                    targetWeight,
                    physicalLevelId,
                    dayGoal,
                    formatSimpleDate(Calendar.getInstance().time, "MM-dd-yyyy")
                )
            ).collectLatest { response ->
                setLoading(false)
                when (response) {
                    is CreateHealthGoalResponse -> {
                        _createHealthGoal.postValue(response.toApiCommonResponse())
                    }

                    is ErrorResponse -> {
                        _createHealthGoal.postValue(response.toApiCommonResponse())
                    }

                    else -> {
                        _createHealthGoal.postValue(null)
                    }
                }
            }
        }
    }
}
