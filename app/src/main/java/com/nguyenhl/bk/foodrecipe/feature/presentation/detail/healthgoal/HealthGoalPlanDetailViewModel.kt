package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.healthgoal

import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.feature.data.repository.HealthGoalRepository

class HealthGoalPlanDetailViewModel constructor(
    val input: BaseInput.HealthGoalPlanDetailInput,
    private val healthGoalRepository: HealthGoalRepository
): BaseViewModel(input) {

    init {

    }
}
