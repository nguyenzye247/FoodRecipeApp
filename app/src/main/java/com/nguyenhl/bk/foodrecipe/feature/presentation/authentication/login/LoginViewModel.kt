package com.nguyenhl.bk.foodrecipe.feature.presentation.authentication.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.ErrorResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.RegisterResponse
import com.nguyenhl.bk.foodrecipe.feature.data.repository.LoginRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginViewModel constructor(
    val input: BaseInput.LoginInput,
    private val loginRepository: LoginRepository
    ): BaseViewModel(input) {
    private val _loginStatus: MutableLiveData<Pair<String, Boolean>?> = MutableLiveData()
    fun liveLoginStatus(): LiveData<Pair<String, Boolean>?> = _loginStatus
    fun loginToAccount(email: String, password: String) {
        viewModelScope.launch {
            loginRepository.loginToAccount(email, password)
                .collectLatest {
                    when (it) {
                        is RegisterResponse -> {
                            _loginStatus.postValue(Pair(it.message, it.status))
                        }

                        is ErrorResponse -> {
                            _loginStatus.postValue(Pair(it.message, it.status))
                        }

                        else -> {
                            _loginStatus.postValue(null)
                        }
                    }
                }
        }
    }
}
