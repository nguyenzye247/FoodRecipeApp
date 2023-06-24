package com.nguyenhl.bk.foodrecipe.feature.presentation.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nguyenhl.bk.foodrecipe.core.extension.getBaseConfig
import com.nguyenhl.bk.foodrecipe.core.extension.observeOnUiThread
import com.nguyenhl.bk.foodrecipe.core.extension.toast
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.AuthStatus
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.ApiHealthStatus
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.toHealthStatus
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.toHealthStatusCategoryDetails
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.userinfo.ApiUserInfo
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.userinfo.toHealthStatus
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.userinfo.toPreferredDish
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.userinfo.toUser
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.ErrorResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.HealthStatusResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.userinfo.UserInfoGetResponse
import com.nguyenhl.bk.foodrecipe.feature.data.repository.DishPreferredRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.HealthStatusRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.UserInfoRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.UserRepository
import com.nguyenhl.bk.foodrecipe.feature.helper.RxBus
import com.nguyenhl.bk.foodrecipe.feature.helper.RxEvent
import com.nguyenhl.bk.foodrecipe.feature.helper.SessionManager
import com.skydoves.sandwich.StatusCode
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
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
    private var timeFinish: Long = 3000
    val isFinish = MutableStateFlow(false)

//    private val _isValidUserInfo: MutableLiveData<Boolean> = MutableLiveData()
//    fun liveIsValidUserInfo(): LiveData<Boolean> = _isValidUserInfo
    private val _isValidUserInfo: MutableLiveData<AuthStatus> = MutableLiveData()
    fun liveIsValidUserInfo(): LiveData<AuthStatus> = _isValidUserInfo

    init {
        viewModelScope.launch(Dispatchers.IO) {
            fetchInitData()
        }
    }

    private suspend fun fetchInitData() {
        viewModelScope.launch {
            val deferredResults = listOf(
                async { getAllHealthStatuses() },
                async { checkForUserInfo() }
            )

            deferredResults.awaitAll()
            closeDelaySplash()
        }
    }

    private suspend fun getAllHealthStatuses() {
        healthStatusRepository.getApiAllHealthStatus().collect { response ->
            when (response) {
                is HealthStatusResponse -> {
                    saveHealthStatusData(response.data)
                }

                else -> {
                    timeFinish = 2500
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
        userInfoRepository.fetchApiUserInfo(token)
            .collect { response ->
                when (response) {
                    is UserInfoGetResponse -> {
                        val infoData = response.info
                        _isValidUserInfo.postValue(AuthStatus.VALID)
                        RxBus.publish(RxEvent.EventValidUserInfo())

                        infoData?.let { info -> saveUserInfoData(info) }
                    }

                    is ErrorResponse -> {
                        if (response.code == StatusCode.Unauthorized.code) {
                            _isValidUserInfo.postValue(AuthStatus.EXPIRED)
                        } else {
                            _isValidUserInfo.postValue(AuthStatus.INVALID)
                        }
                    }

                    else -> {
                        //TODO: Modify this for redirect to Error screen
                        _isValidUserInfo.postValue(AuthStatus.OTHER)
                    }
                }
            }
    }

    private suspend fun saveHealthStatusData(healthStatusData: List<ApiHealthStatus>) {
        val healthStatuses = healthStatusData.map { it.toHealthStatus() }
        val healthStatusCatDetails = healthStatusData.flatMap { it.toHealthStatusCategoryDetails() }
        healthStatusRepository.saveAllHealthStatuses(healthStatuses)
        healthStatusRepository.saveAllHealthStatusCatDetails(healthStatusCatDetails)
    }

    private suspend fun saveUserInfoData(userInfoApi: ApiUserInfo) {
        val user = userInfoApi.toUser()
        input.application.getBaseConfig().userId = user.userId

        val preferredDishes = userInfoApi.preferredDishes
            .map {
                it.toPreferredDish(user.userId)
            }
        val healthStatus = userInfoApi.healthStatus.toHealthStatus(user.userId)

        dishPreferredRepository.saveAllPreferredDishes(preferredDishes)
        healthStatusRepository.saveHealthStatus(healthStatus)
        userRepository.saveUser(user)
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
