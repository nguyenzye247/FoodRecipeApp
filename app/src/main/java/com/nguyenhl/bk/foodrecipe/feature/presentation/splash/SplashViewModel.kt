package com.nguyenhl.bk.foodrecipe.feature.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nguyenhl.bk.foodrecipe.core.extension.observeOnUiThread
import com.nguyenhl.bk.foodrecipe.core.extension.toast
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.toHealthStatus
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.userinfo.ApiUserInfo
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.userinfo.toHealthStatus
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.userinfo.toPreferredDish
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.userinfo.toUser
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.HealthStatusResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.userinfo.UserInfoGetResponse
import com.nguyenhl.bk.foodrecipe.feature.data.repository.DishPreferredRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.HealthStatusRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.UserInfoRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.UserRepository
import com.nguyenhl.bk.foodrecipe.feature.helper.SessionManager
import com.nguyenhl.bk.foodrecipe.feature.util.DispatchGroup
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SplashViewModel constructor(
    val input: BaseInput.SplashInput,
    private val healthStatusRepository: HealthStatusRepository,
    private val dishPreferredRepository: DishPreferredRepository,
    private val userRepository: UserRepository,
    private val userInfoRepository: UserInfoRepository
) : BaseViewModel(input) {
    private val dispatchGroup = DispatchGroup(Dispatchers.IO)
    private var timeFinish: Long = 3000
    val isFinish = MutableStateFlow(false)

    private val _isValidUserInfo: MutableLiveData<Boolean> = MutableLiveData()
    fun liveIsValidUserInfo(): LiveData<Boolean> = _isValidUserInfo

    init {
        viewModelScope.launch {
            fetchInitData()
        }
    }

    private suspend fun fetchInitData() {
        dispatchGroup.apply {
            async {
                getAllHealthStatuses()
            }
            async {
                checkForUserInfo()
            }
        }
        dispatchGroup.awaitAll {
            closeDelaySplash()
        }
    }

    private suspend fun getAllHealthStatuses() {
        healthStatusRepository.getApiAllHealthStatus().collectLatest { response ->
            when (response) {
                is HealthStatusResponse -> {
                    healthStatusRepository.saveAllHealthStatuses(response.data.map { it.toHealthStatus() })
                }

                else -> {
                    timeFinish = 4000
                }
            }
        }
    }

    private suspend fun checkForUserInfo() {
        val token = SessionManager.fetchToken(input.application).ifEmpty {
            input.application.toast("Empty token")
            return
        }
        Timber.tag("TOKEN").d(token)
        userInfoRepository.getApiUserInfo(token)
            .collectLatest { response ->
                when (response) {
                    is UserInfoGetResponse -> {
                        val status = response.status
                        val infoData = response.info
                        _isValidUserInfo.postValue(status)

                        infoData?.let { info -> saveUserInfoData(info) }
                    }

                    else -> {
                        _isValidUserInfo.postValue(false)
                    }
                }
            }
    }

    private suspend fun saveUserInfoData(userInfoApi: ApiUserInfo) {
        val user = userInfoApi.toUser()
        val preferredDishes = userInfoApi.preferredDishes
            .map {
                it.toPreferredDish(user.userId)
            }
        val healthStatus = userInfoApi.healthStatus.toHealthStatus(user.userId)
        userRepository.saveUser(user)
        dishPreferredRepository.saveAllPreferredDishes(preferredDishes)
        healthStatusRepository.saveHealthStatus(healthStatus)
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
