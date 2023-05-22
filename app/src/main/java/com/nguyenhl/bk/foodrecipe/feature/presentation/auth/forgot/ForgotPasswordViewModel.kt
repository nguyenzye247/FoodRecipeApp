package com.nguyenhl.bk.foodrecipe.feature.presentation.auth.forgot

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.ErrorResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.auth.ForgotPasswordResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.auth.LoginResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.auth.toApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.toApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.data.repository.auth.ForgotPasswordRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.ApiCommonResponse
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

class ForgotPasswordViewModel constructor(
    val input: BaseInput.ForgotPasswordInput,
    private val forgotPasswordRepository: ForgotPasswordRepository
): BaseViewModel(input) {

    private val _forgotPasswordStatus: MutableLiveData<ApiCommonResponse?> = MutableLiveData()
    fun liveForgotPasswordStatus(): LiveData<ApiCommonResponse?> = _forgotPasswordStatus

    fun sendForgotPassword(email: String) {
        viewModelScope.launch {
            forgotPasswordRepository.sendForgotPasword(email).collectLatest { response ->
                when (response) {
                    is ForgotPasswordResponse -> {
                        _forgotPasswordStatus.postValue(response.toApiCommonResponse())
                    }

                    is ErrorResponse -> {
                        _forgotPasswordStatus.postValue(response.toApiCommonResponse())
                    }

                    else -> {
                        _forgotPasswordStatus.postValue(null)
                    }
                }
            }
        }
    }
}
