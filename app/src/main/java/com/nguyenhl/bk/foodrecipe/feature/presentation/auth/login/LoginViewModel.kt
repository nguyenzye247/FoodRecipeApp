package com.nguyenhl.bk.foodrecipe.feature.presentation.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nguyenhl.bk.foodrecipe.core.extension.getBaseConfig
import com.nguyenhl.bk.foodrecipe.core.extension.ifNotEmpty
import com.nguyenhl.bk.foodrecipe.core.extension.toast
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.AuthStatus
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.userinfo.ApiUserInfo
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.userinfo.toHealthStatus
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.userinfo.toPreferredDish
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.userinfo.toUser
import com.nguyenhl.bk.foodrecipe.feature.dto.ApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.ErrorResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.auth.LoginResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.userinfo.UserInfoGetResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.auth.toApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.toApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.data.repository.DishPreferredRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.HealthStatusRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.auth.LoginRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.UserInfoRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.UserRepository
import com.nguyenhl.bk.foodrecipe.feature.helper.SessionManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel constructor(
    val input: BaseInput.LoginInput,
    private val loginRepository: LoginRepository,
    private val userInfoRepository: UserInfoRepository,
    private val healthStatusRepository: HealthStatusRepository,
    private val dishPreferredRepository: DishPreferredRepository,
    private val userRepository: UserRepository,
) : BaseViewModel(input) {

    private val _loginStatus: MutableLiveData<ApiCommonResponse?> = MutableLiveData()
    fun liveLoginStatus(): LiveData<ApiCommonResponse?> = _loginStatus

//    private val _isValidUserInfo: MutableLiveData<Boolean> = MutableLiveData()
//    fun liveIsValidUserInfo(): LiveData<Boolean> = _isValidUserInfo
    private val _isValidUserInfo: MutableLiveData<AuthStatus> = MutableLiveData()
    fun liveIsValidUserInfo(): LiveData<AuthStatus> = _isValidUserInfo

    fun loginToAccount(email: String, password: String) {
        viewModelScope.launch {
            loginRepository.loginToAccount(email, password)
                .collectLatest { response ->
                    when (response) {
                        is LoginResponse -> {
                            _loginStatus.postValue(response.toApiCommonResponse())
                            saveLoginToken(response.token)
                            Timber.tag("TOKEN_1").d(response.token)
                        }

                        is ErrorResponse -> {
                            _loginStatus.postValue(response.toApiCommonResponse())
                        }

                        else -> {
                            _loginStatus.postValue(null)
                        }
                    }
                }
        }
    }

    fun checkForUserInfo() {
        val token = SessionManager.fetchToken(input.application).ifEmpty {
            input.application.toast("Empty token")
            return
        }
        viewModelScope.launch {
            userInfoRepository.fetchApiUserInfo(token)
                .collectLatest { response ->
                    when (response) {
                        is UserInfoGetResponse -> {
                            _isValidUserInfo.postValue(AuthStatus.VALID)
                            response.info?.let {
                                saveUserInfoData(it)
                            }
                        }

                        is ErrorResponse -> {
                            if (response.message.contains("jwt")) {
                                _isValidUserInfo.postValue(AuthStatus.EXPIRED)
                            } else {
                                _isValidUserInfo.postValue(AuthStatus.INVALID)
                            }
                        }

                        else -> {
                            _isValidUserInfo.postValue(AuthStatus.INVALID)
                        }
                    }
                }
        }
    }

    fun doOnUserLoggedIn(action: () -> Unit) {
        if (SessionManager.isTokenSaved(input.application)) {
            action.invoke()
        }
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

    private fun saveLoginToken(token: String) {
        token.ifNotEmpty {
            SessionManager.saveToken(input.application, it)
        }
    }
}
