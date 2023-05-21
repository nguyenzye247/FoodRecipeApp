package com.nguyenhl.bk.foodrecipe.feature.presentation.splash

import androidx.lifecycle.viewModelScope
import com.nguyenhl.bk.foodrecipe.core.extension.observeOnUiThread
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.toHealthStatus
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.ErrorResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.HealthStatusResponse
import com.nguyenhl.bk.foodrecipe.feature.data.repository.HealthStatusRepository
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class SplashViewModel constructor(
    val input: BaseInput.NoInput,
    private val healthStatusRepository: HealthStatusRepository
) : BaseViewModel(input) {
    private var timeFinish: Long = 3000
    val isFinish = MutableStateFlow(false)

    init {
        getAllHealthStatuses()
    }

    private fun getAllHealthStatuses() {
        viewModelScope.launch {
            healthStatusRepository.getApiAllHealthStatus().collectLatest { response ->
                when (response) {
                    is HealthStatusResponse -> {
                        healthStatusRepository.saveAllHealthStatuses(response.data.map { it.toHealthStatus() })
                        closeDelaySplash()
                    }

                    else -> {
                        timeFinish = 4000
                        closeDelaySplash()
                    }
                }
            }
        }
    }

    private fun closeDelaySplash() {
        addDisposables(
            Observable.timer(timeFinish, TimeUnit.MILLISECONDS)
                .observeOnUiThread()
                .subscribe {
                    isFinish.value = true
                })
    }
}
