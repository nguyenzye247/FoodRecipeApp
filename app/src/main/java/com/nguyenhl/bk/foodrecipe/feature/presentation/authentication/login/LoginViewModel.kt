package com.nguyenhl.bk.foodrecipe.feature.presentation.authentication.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nguyenhl.bk.foodrecipe.core.extension.ifNotEmpty
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.model.AuthenticationModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.ErrorResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.LoginResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.UserInfoResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.toAuthenticationModel
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

    private val _loginStatus: MutableLiveData<AuthenticationModel?> = MutableLiveData()
    fun liveLoginStatus(): LiveData<AuthenticationModel?> = _loginStatus

    private val _isValidUserInfo: MutableLiveData<Boolean> = MutableLiveData()
    fun liveIsValidUserInfo(): LiveData<Boolean> = _isValidUserInfo

    fun loginToAccount(email: String, password: String) {
        viewModelScope.launch {
            loginRepository.loginToAccount(email, password)
                .collectLatest {
                    when (it) {
                        is LoginResponse -> {
                            _loginStatus.postValue(it.toAuthenticationModel())
                            saveLoginToken(it.token)
                            Timber.tag("TOKEN_1").d(it.token)
                        }

                        is ErrorResponse -> {
                            _loginStatus.postValue(it.toAuthenticationModel())
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
            userInfoRepository.getUserInfoService(token)
                .collectLatest {
                    when (it) {
                        is UserInfoResponse -> {
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

    private fun saveLoginToken(token: String) {
        token.ifNotEmpty {
            SessionManager.saveToken(input.application, it)
        }
    }
}
