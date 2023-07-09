package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.healthgoal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nguyenhl.bk.foodrecipe.core.extension.toast
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.healthgoal.CreateHealthIndicatorBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.healthgoal.GetHealthIndicatorBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.mapper.CreateHealthIndicatorErrorResponseMapper
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.healthgoal.indicator.toHealthIndicatorDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.healthgoal.toHealthGoalDetailDto
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.ErrorResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.healthgoal.HealthGoalDetailResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.healthgoal.indicator.CreateHealthIndicatorResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.healthgoal.indicator.GetHealthIndicatorResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.healthgoal.indicator.toApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.toApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.data.repository.HealthGoalRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.HealthIndicatorRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.ApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.dto.healthgoal.HealthGoalDetailDto
import com.nguyenhl.bk.foodrecipe.feature.dto.healthgoal.HealthIndicatorDto
import com.nguyenhl.bk.foodrecipe.feature.helper.SessionManager
import com.nguyenhl.bk.foodrecipe.feature.util.DateFormatUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.*

class HealthGoalPlanDetailViewModel constructor(
    val input: BaseInput.HealthGoalPlanDetailInput,
    private val healthGoalRepository: HealthGoalRepository,
    private val healthIndicatorRepository: HealthIndicatorRepository
) : BaseViewModel(input) {

    private val _healthGoalDetail: MutableLiveData<HealthGoalDetailDto?> = MutableLiveData()
    val liveHealthGoalDetail: LiveData<HealthGoalDetailDto?> = _healthGoalDetail

    private val _todayHealthIndicator: MutableLiveData<List<HealthIndicatorDto>?> =
        MutableLiveData()
    val liveTodayHealthIndicator: LiveData<List<HealthIndicatorDto>?> = _todayHealthIndicator

    private val _weightIndicator: MutableLiveData<List<HealthIndicatorDto>?> =
        MutableLiveData()
    val liveWeightIndicator: LiveData<List<HealthIndicatorDto>?> = _weightIndicator

    private val _heartRateIndicator: MutableLiveData<List<HealthIndicatorDto>?> =
        MutableLiveData()
    val liveHeartRateIndicator: LiveData<List<HealthIndicatorDto>?> = _heartRateIndicator

    private val _bloodSugarIndicator: MutableLiveData<List<HealthIndicatorDto>?> =
        MutableLiveData()
    val liveBloodSugarIndicator: LiveData<List<HealthIndicatorDto>?> = _bloodSugarIndicator

    private val _createHealthIndicator: MutableLiveData<ApiCommonResponse?> = MutableLiveData()
    val liveCreateHealthIndicator: LiveData<ApiCommonResponse?> = _createHealthIndicator

    init {
        val healthGoal = input.healthGoalPlanDto
        healthGoal?.let {
            fetchHealthGoalDetails(it.apiId)
            fetchTodayHealthIndicators(it.apiId)
        }
        fetchIndicators()
    }

    fun fetchIndicators() {
        input.healthGoalPlanDto?.let {
            fetchWeightIndicator(it.apiId)
            fetchHeartRateIndicator(it.apiId)
            fetchBloodSugarIndicator(it.apiId)
        }
    }

    private fun fetchHealthGoalDetails(healthGoalId: String) {
        val token = SessionManager.fetchToken(input.application).ifEmpty {
            input.application.toast("Empty token")
            return
        }
        setLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            healthGoalRepository.fetchHealthGoalDetail(token, healthGoalId)
                .collectLatest { response ->
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

    private fun fetchTodayHealthIndicators(healthGoalId: String) {
        val token = SessionManager.fetchToken(input.application).ifEmpty {
            input.application.toast("Empty token")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            val today = DateFormatUtil.formatSimpleDate(
                Calendar.getInstance().time,
                "MM-dd-yyyy"
            )
            healthIndicatorRepository.fetchHealthIndicatorByDate(
                token,
                healthGoalId,
                GetHealthIndicatorBody(today)
            ).collectLatest { response ->
                when (response) {
                    is GetHealthIndicatorResponse -> {
                        _todayHealthIndicator.postValue(response.healthIndicators.map { it.toHealthIndicatorDto() })
                    }

                    else -> {
                        _todayHealthIndicator.postValue(null)
                    }
                }
            }
        }
    }

    private fun fetchWeightIndicator(healthGoalId: String) {
        val token = SessionManager.fetchToken(input.application).ifEmpty {
            input.application.toast("Empty token")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            healthIndicatorRepository.fetchWeightIndicator(token, healthGoalId)
                .collectLatest { response ->
                    when (response) {
                        is GetHealthIndicatorResponse -> {
                            _weightIndicator.postValue(response.healthIndicators.map { it.toHealthIndicatorDto() })
                        }

                        else -> {
                            _weightIndicator.postValue(null)
                        }
                    }
                }
        }
    }

    private fun fetchHeartRateIndicator(healthGoalId: String) {
        val token = SessionManager.fetchToken(input.application).ifEmpty {
            input.application.toast("Empty token")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            healthIndicatorRepository.fetchHeartRateIndicator(token, healthGoalId)
                .collectLatest { response ->
                    when (response) {
                        is GetHealthIndicatorResponse -> {
                            _heartRateIndicator.postValue(response.healthIndicators.map { it.toHealthIndicatorDto() })
                        }

                        else -> {
                            _heartRateIndicator.postValue(null)
                        }
                    }
                }
        }
    }

    private fun fetchBloodSugarIndicator(healthGoalId: String) {
        val token = SessionManager.fetchToken(input.application).ifEmpty {
            input.application.toast("Empty token")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            healthIndicatorRepository.fetchBloodSugarIndicator(token, healthGoalId)
                .collectLatest { response ->
                    when (response) {
                        is GetHealthIndicatorResponse -> {
                            _bloodSugarIndicator.postValue(response.healthIndicators.map { it.toHealthIndicatorDto() })
                        }

                        else -> {
                            _bloodSugarIndicator.postValue(null)
                        }
                    }
                }
        }
    }

    fun createHealthIndicator(
        weight: Float,
        heartRate: Int,
        bloodSugar: Int
    ) {
        val healthGoalId = input.healthGoalPlanDto?.apiId ?: return
        val token = SessionManager.fetchToken(input.application).ifEmpty {
            input.application.toast("Empty token")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            healthIndicatorRepository.createHealthIndicator(
                token,
                healthGoalId,
                CreateHealthIndicatorBody(weight, bloodSugar, heartRate,
                    DateFormatUtil.formatSimpleDate(Calendar.getInstance().time, "MM-dd-yyyy")
                )
            ).collectLatest { response ->
                when (response) {
                    is CreateHealthIndicatorResponse -> {
                        _createHealthIndicator.postValue(response.toApiCommonResponse())
                    }

                    is ErrorResponse -> {
                        _createHealthIndicator.postValue(response.toApiCommonResponse())
                    }

                    else -> {
                        _createHealthIndicator.postValue(null)
                    }
                }
            }
        }
    }
}
