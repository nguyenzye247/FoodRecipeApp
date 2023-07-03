package com.nguyenhl.bk.foodrecipe.feature.presentation.weeklyplan

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observeDistinct
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.core.extension.views.setVisible
import com.nguyenhl.bk.foodrecipe.databinding.ActivityWeeklyPlanBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.dto.weeklyplan.WeeklyPlanDto
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.weeklyplan.WeeklyPlanDetailActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.weeklyplan.WeeklyPlanDetailActivity.Companion.KEY_WEEKLY_PLAN_DETAIL_DTO
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class WeeklyPlanActivity : BaseActivity<ActivityWeeklyPlanBinding, WeeklyPlanViewModel>() {
    private lateinit var weeklyPlanAdapter: WeeklyPlanAdapter

    override fun getLazyBinding() = lazy { ActivityWeeklyPlanBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModel<WeeklyPlanViewModel> {
        parametersOf(
            BaseInput.WeeklyPlanInput(
                application
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

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    override fun initObservers() {
        viewModel.apply {
            observe(liveWeeklyPlan()) { weeklyPlans ->
                weeklyPlans?.let {
                    bindWeeklyPlansDataView(it)
                }
            }

            observeDistinct(liveIsLoading()) { isLoading ->
                showLoadingView(isLoading == true)
            }
        }
    }

    private fun bindWeeklyPlansDataView(weeklyPlans: List<WeeklyPlanDto>) {
        binding.rvWeeklyPlan.apply {
            weeklyPlanAdapter = WeeklyPlanAdapter(weeklyPlans) { weeklyPlan ->
                goToWeeklyPlanDetail(weeklyPlan)
            }
            adapter = weeklyPlanAdapter
            layoutManager = LinearLayoutManager(
                this@WeeklyPlanActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

    private fun showLoadingView(isShow: Boolean) {
        binding.loading.apply {
            backgroundView.setVisible(false)
            progressBar.setVisible(isShow)
        }
    }

    private fun goToWeeklyPlanDetail(weeklyPlan: WeeklyPlanDto) {
        WeeklyPlanDetailActivity.startActivity(this@WeeklyPlanActivity) {
            putExtra(KEY_WEEKLY_PLAN_DETAIL_DTO, weeklyPlan)
        }
    }

    companion object {
        fun startActivity(context: Context?, configIntent: Intent.() -> Unit) {
            context?.let {
                it.start<WeeklyPlanActivity> {
                    apply(configIntent)
                }
            }
        }
    }
}
