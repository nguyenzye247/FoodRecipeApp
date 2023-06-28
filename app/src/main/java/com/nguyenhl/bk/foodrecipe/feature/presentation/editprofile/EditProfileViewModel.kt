package com.nguyenhl.bk.foodrecipe.feature.presentation.editprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.nguyenhl.bk.foodrecipe.core.extension.getBaseConfig
import com.nguyenhl.bk.foodrecipe.core.extension.toast
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.body.userinfo.UserInfoPutBody
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.ErrorResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.toApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.userinfo.UserInfoPostResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.userinfo.UserInfoPutResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.api.response.userinfo.toApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.HealthStatus
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.toUserInfoDto
import com.nguyenhl.bk.foodrecipe.feature.data.repository.HealthStatusRepository
import com.nguyenhl.bk.foodrecipe.feature.data.repository.UserInfoRepository
import com.nguyenhl.bk.foodrecipe.feature.dto.*
import com.nguyenhl.bk.foodrecipe.feature.dto.toUserInfoPutBody
import com.nguyenhl.bk.foodrecipe.feature.helper.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EditProfileViewModel constructor(
    val input: BaseInput.EditProfileInput,
    private val userInfoRepository: UserInfoRepository,
    private val healthStatusRepository: HealthStatusRepository
) : BaseViewModel(input) {
    val userId: String = input.application.getBaseConfig().userId

    private val _userInfo: MutableLiveData<UserInfoDto?> = MutableLiveData()
    fun liveUserInfo(): LiveData<UserInfoDto?> = _userInfo
    fun initUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            _userInfo.postValue(userInfoRepository.getUserInfoByUserId(userId)?.toUserInfoDto())
        }
    }

    private val _updateInfoStatus: MutableLiveData<ApiCommonResponse?> = MutableLiveData()
    fun liveUpdateInfoStatus(): LiveData<ApiCommonResponse?> = _updateInfoStatus

    var selectedHealthStatus: HealthStatusDto = HealthStatusDto.noneHealthStatus

    suspend fun getAllDbHealthStatuses(): ArrayList<HealthStatus> {
        return healthStatusRepository.getAllDbHealthStatus() as ArrayList<HealthStatus>
    }

    fun updateUserInfo(userDto: UserInfoDto) {
        val token = SessionManager.fetchToken(input.application).ifEmpty {
            input.application.toast("Empty token")
            return
        }

        val userInfoPutBody = userDto.toUserInfoPutBody()
        viewModelScope.launch(Dispatchers.IO) {
            userInfoRepository.updateApiUserInfo(token, userInfoPutBody)
                .collectLatest { response ->
                    when (response) {
                        is UserInfoPutResponse -> {
                            _updateInfoStatus.postValue(response.toApiCommonResponse())
                            try {
                                userInfoRepository.updateUser(userDto.toUser())
                            } catch (ex: Exception) {
                                ex.printStackTrace()
                            }
                        }
                        is ErrorResponse -> {
                            _updateInfoStatus.postValue(response.toApiCommonResponse())
                        }
                        else -> {
                            _updateInfoStatus.postValue(null)
                        }
                    }
                }
        }
    }
}
