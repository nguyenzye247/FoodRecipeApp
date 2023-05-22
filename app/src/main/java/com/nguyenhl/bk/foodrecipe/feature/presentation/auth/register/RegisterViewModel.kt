package com.nguyenhl.bk.foodrecipe.feature.presentation.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.dto.ApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.ErrorResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.auth.RegisterResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.auth.toApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.toApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.data.repository.auth.RegisterRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RegisterViewModel constructor(
    val input: BaseInput.RegisterInput,
    private val registerRepository: RegisterRepository
) : BaseViewModel(input) {
    private val _registerStatus: MutableLiveData<ApiCommonResponse?> = MutableLiveData()
    fun liveRegisterStatus(): LiveData<ApiCommonResponse?> = _registerStatus

    fun registerNewAccount(
        email: String,
        password: String,
        confirmedPassword: String
    ) {
        viewModelScope.launch {
            registerRepository.registerNewAccount(email, password, confirmedPassword)
                .collectLatest {
                    when (it) {
                        is RegisterResponse -> {
                            _registerStatus.postValue(it.toApiCommonResponse())
                        }

                        is ErrorResponse -> {
                            _registerStatus.postValue(it.toApiCommonResponse())
                        }

                        else -> {
                            _registerStatus.postValue(null)
                        }
                    }
                }
        }
    }
}
