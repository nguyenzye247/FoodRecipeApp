package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.healthgoal

import android.content.Context
import android.content.Intent
import com.nguyenhl.bk.foodrecipe.core.extension.parcelableExtra
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.databinding.ActivityHealthGoalDetailBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.presentation.healthgoal.HealthGoalPlanActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HealthGoalPlanDetailActivity :
    BaseActivity<ActivityHealthGoalDetailBinding, HealthGoalPlanDetailViewModel>() {
    override fun getLazyBinding() = lazy { ActivityHealthGoalDetailBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModel<HealthGoalPlanDetailViewModel> {
        parametersOf(
            BaseInput.HealthGoalPlanDetailInput(
                application,
                intent.parcelableExtra(HealthGoalPlanActivity.KEY_HEALTH_GOAL_PLAN)
            )
        )
    }

    override fun initViews() {
        binding.apply {

        }
    }

    override fun initListener() {
        binding.apply {
            btnBack.onClick {
                onBackPressed()
            }
        }
    }

    override fun initObservers() {
        viewModel.apply {

        }
    }

    companion object {

        fun startActivity(context: Context?, configIntent: Intent.() -> Unit) {
            context?.let {
                it.start<HealthGoalPlanDetailActivity> {
                    apply(configIntent)
                }
            }
        }
    }
}
