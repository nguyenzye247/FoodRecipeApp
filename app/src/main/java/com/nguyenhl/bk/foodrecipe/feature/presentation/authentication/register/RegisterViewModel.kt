package com.nguyenhl.bk.foodrecipe.feature.presentation.authentication.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.ErrorResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.RegisterResponse
import com.nguyenhl.bk.foodrecipe.feature.data.repository.RegisterRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RegisterViewModel constructor(
    val input: BaseInput.RegisterInput,
    private val registerRepository: RegisterRepository
) : BaseViewModel(input) {
    private val _registerStatus: MutableLiveData<Pair<String, Boolean>?> = MutableLiveData()
    fun liveRegisterStatus(): LiveData<Pair<String, Boolean>?> = _registerStatus

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
                            _registerStatus.postValue(Pair(it.message, it.status))
                        }

                        is ErrorResponse -> {
                            _registerStatus.postValue(Pair(it.message, it.status))
                        }

                        else -> {
                            _registerStatus.postValue(null)
                        }
                    }
                }
        }
    }
}
