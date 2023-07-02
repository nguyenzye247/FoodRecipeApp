package com.nguyenhl.bk.foodrecipe.feature.presentation.healthgoal

import android.content.Context
import android.content.Intent
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.databinding.ActivityHealthGoalPlanBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HealthGoalPlanActivity: BaseActivity<ActivityHealthGoalPlanBinding, HealthGoalPlanViewModel>() {


    override fun getLazyBinding() = lazy { ActivityHealthGoalPlanBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModel<HealthGoalPlanViewModel> {
        parametersOf(BaseInput.HealthGoalPlanInput(
            application
        ))
    }

    override fun initViews() {

    }

    override fun initListener() {
        binding.apply {
            btnTest.onClick {

            }
        }
    }

    override fun initObservers() {

    }

    companion object {
        fun startActivity(context: Context?, configIntent: Intent.() -> Unit) {
            context?.let {
                it.start<HealthGoalPlanActivity> {
                    apply(configIntent)
                }
            }
        }
    }
}
