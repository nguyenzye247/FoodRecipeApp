package com.nguyenhl.bk.foodrecipe.feature.presentation.createinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nguyenhl.bk.foodrecipe.core.extension.getBaseConfig
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.AuthStatus
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.ErrorResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.auth.toApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.toApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.userinfo.UserInfoGetResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.userinfo.UserInfoPostResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.userinfo.toApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.HealthStatus
import com.nguyenhl.bk.foodrecipe.feature.data.repository.HealthStatusRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.UserInfoRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.UserRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.ApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.dto.HealthStatusDto
import com.nguyenhl.bk.foodrecipe.feature.dto.UserInfoDto
import com.nguyenhl.bk.foodrecipe.feature.dto.toUserInfoPostBody
import com.nguyenhl.bk.foodrecipe.feature.helper.RxBus
import com.nguyenhl.bk.foodrecipe.feature.helper.RxEvent
import com.nguyenhl.bk.foodrecipe.feature.helper.SessionManager
import com.skydoves.sandwich.StatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CreateInfoViewModel constructor(
    val input: BaseInput.CreateInfoInput,
    private val userInfoRepository: UserInfoRepository,
    private val healthStatusRepository: HealthStatusRepository
) : BaseViewModel(input) {
    private val _createInfoStatus: MutableLiveData<ApiCommonResponse?> = MutableLiveData()
    fun liveCreateInfoStatus(): LiveData<ApiCommonResponse?> = _createInfoStatus

    var selectedHealthStatus: HealthStatusDto = HealthStatusDto.noneHealthStatus

    suspend fun getAllDbHealthStatuses(): ArrayList<HealthStatus> {
        return healthStatusRepository.getAllDbHealthStatus() as ArrayList<HealthStatus>
    }

    fun createUserInfo(userInfoDto: UserInfoDto) {
        viewModelScope.launch(Dispatchers.IO) {
            val userInfoPostBody = userInfoDto.toUserInfoPostBody()
            val token = SessionManager.fetchToken(input.application)
            userInfoRepository.createApiUserInfo(token, userInfoPostBody)
                .collectLatest { response ->
                    when (response) {
                        is UserInfoPostResponse -> {
                            fetchUserInfo {
                                _createInfoStatus.postValue(response.toApiCommonResponse())
                            }
                        }
                        is ErrorResponse -> {
                            _createInfoStatus.postValue(response.toApiCommonResponse())
                        }
                        else -> {
                            _createInfoStatus.postValue(null)
                        }
                    }
                }
        }
    }

    private fun fetchUserInfo(onfetched: () -> Unit) {
        val token = SessionManager.fetchToken(input.application)
        viewModelScope.launch(Dispatchers.IO) {
            userInfoRepository.fetchApiUserInfo(token).collectLatest { response ->
                when (response) {
                    is UserInfoGetResponse -> {
                        val infoData = response.info
                        infoData?.let { info ->
                            input.application.getBaseConfig().userId = info.id
                        }
                        onfetched()
                    }

                    else -> {
                        _createInfoStatus.postValue(null)
                        onfetched()
                    }
                }
            }
        }
    }

}
