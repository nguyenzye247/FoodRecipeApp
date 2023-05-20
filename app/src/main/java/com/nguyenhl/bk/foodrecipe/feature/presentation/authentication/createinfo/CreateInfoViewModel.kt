package com.nguyenhl.bk.foodrecipe.feature.presentation.authentication.createinfo

import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.HealthStatus
import com.nguyenhl.bk.foodrecipe.feature.data.repository.HealthStatusRepository

class CreateInfoViewModel constructor(
    val input: BaseInput.CreateInfoInput,
    private val healthStatusRepository: HealthStatusRepository
) : BaseViewModel(input) {

    suspend fun getAllDbHealthStatuses(): ArrayList<HealthStatus> {
        return healthStatusRepository.getAllDbHealthStatus() as ArrayList<HealthStatus>
    }

}
