package com.nguyenhl.bk.foodrecipe.feature.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nguyenhl.bk.foodrecipe.core.extension.observeOnUiThread
import com.nguyenhl.bk.foodrecipe.core.extension.toast
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.toHealthStatus
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.ErrorResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.HealthStatusResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.userinfo.UserInfoGetResponse
import com.nguyenhl.bk.foodrecipe.feature.data.repository.CategoryRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.HealthStatusRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.UserInfoRepository
import com.nguyenhl.bk.foodrecipe.feature.helper.SessionManager
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class SplashViewModel constructor(
    val input: BaseInput.SplashInput,
    private val healthStatusRepository: HealthStatusRepository,
    private val userInfoRepository: UserInfoRepository
) : BaseViewModel(input) {
    private var timeFinish: Long = 3000
    val isFinish = MutableStateFlow(false)

    private val _isValidUserInfo: MutableLiveData<Boolean> = MutableLiveData()
    fun liveIsValidUserInfo(): LiveData<Boolean> = _isValidUserInfo

    init {
        getAllHealthStatuses()
        checkForUserInfo()
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

    private fun checkForUserInfo() {
        val token = SessionManager.fetchToken(input.application).ifEmpty {
            input.application.toast("Empty token")
            return
        }
        viewModelScope.launch {
            userInfoRepository.getApiUserInfo(token)
                .collectLatest {
                    when (it) {
                        is UserInfoGetResponse -> {
                            _isValidUserInfo.postValue(true)
                        }

                        else -> {
                            _isValidUserInfo.postValue(false)
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

    fun doOnUserLoggedIn(action: () -> Unit): Boolean {
        val isTokenSaved = SessionManager.isTokenSaved(input.application)
        if (isTokenSaved) {
            action.invoke()
        }
        return isTokenSaved
    }
}
