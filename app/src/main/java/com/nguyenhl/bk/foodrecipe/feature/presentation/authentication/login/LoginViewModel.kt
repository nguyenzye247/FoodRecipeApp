package com.nguyenhl.bk.foodrecipe.feature.presentation.authentication.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nguyenhl.bk.foodrecipe.core.extension.ifNotEmpty
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.dto.ApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.ErrorResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.LoginResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.userinfo.UserInfoGetResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.toApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.data.repository.LoginRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.UserInfoRepository
import com.nguyenhl.bk.foodrecipe.feature.helper.SessionManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginViewModel constructor(
    val input: BaseInput.LoginInput,
    private val loginRepository: LoginRepository,
    private val userInfoRepository: UserInfoRepository
) : BaseViewModel(input) {

    private val _loginStatus: MutableLiveData<ApiCommonResponse?> = MutableLiveData()
    fun liveLoginStatus(): LiveData<ApiCommonResponse?> = _loginStatus

    private val _isValidUserInfo: MutableLiveData<Boolean> = MutableLiveData()
    fun liveIsValidUserInfo(): LiveData<Boolean> = _isValidUserInfo

    fun loginToAccount(email: String, password: String) {
        viewModelScope.launch {
            loginRepository.loginToAccount(email, password)
                .collectLatest {
                    when (it) {
                        is LoginResponse -> {
                            _loginStatus.postValue(it.toApiCommonResponse())
                            saveLoginToken(it.token)
                            Timber.tag("TOKEN_1").d(it.token)
                        }

                        is ErrorResponse -> {
                            _loginStatus.postValue(it.toApiCommonResponse())
                        }

                        else -> {
                            _loginStatus.postValue(null)
                        }
                    }
                }
        }
    }

    fun checkForUserInfo(onTokenEmptyCallback: () -> Unit) {
        val token = SessionManager.fetchToken(input.application).ifEmpty {
            onTokenEmptyCallback.invoke()
            return
        }
        viewModelScope.launch {
            userInfoRepository.getApiUserInfo(token)
                .collectLatest {
                    when (it) {
                        is UserInfoGetResponse -> {
                            _isValidUserInfo.postValue(true)
                        }

                        is ErrorResponse -> {
                            _isValidUserInfo.postValue(false)
                        }

                        else -> {
                            _isValidUserInfo.postValue(false)
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

    private fun saveLoginToken(token: String) {
        token.ifNotEmpty {
            SessionManager.saveToken(input.application, it)
        }
    }
}
