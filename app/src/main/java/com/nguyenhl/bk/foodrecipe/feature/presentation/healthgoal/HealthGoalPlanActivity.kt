package com.nguyenhl.bk.foodrecipe.feature.presentation.healthgoal

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observeDistinct
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.toastError
import com.nguyenhl.bk.foodrecipe.core.extension.toastSuccess
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.core.extension.views.setVisible
import com.nguyenhl.bk.foodrecipe.databinding.ActivityHealthGoalPlanBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.dto.ApiCommonResponse
import com.nguyenhl.bk.foodrecipe.feature.dto.healthgoal.HealthGoalDto
import com.nguyenhl.bk.foodrecipe.feature.dto.healthgoal.PhysicalLevelDto
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.healthgoal.HealthGoalPlanDetailActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.healthgoal.create.CreateHealthGoalDialog
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HealthGoalPlanActivity: BaseActivity<ActivityHealthGoalPlanBinding, HealthGoalPlanViewModel>() {
    private val physicalLevels: ArrayList<PhysicalLevelDto> = arrayListOf()
    private val healthGoals: ArrayList<HealthGoalDto> = arrayListOf()

    private lateinit var healthGoalAdapter: HealthGoalAdapter
    private var createHealthGoalDialog: CreateHealthGoalDialog? = null

    override fun getLazyBinding() = lazy { ActivityHealthGoalPlanBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModel<HealthGoalPlanViewModel> {
        parametersOf(BaseInput.HealthGoalPlanInput(
            application
        ))
    }

    override fun initViews() {
        binding.apply {
            healthGoalAdapter = HealthGoalAdapter(healthGoals) { healthGoal ->
                goToHealthGoalPlanDetail(healthGoal)
            }
            rvHealthGoal.apply {
                adapter = healthGoalAdapter
                layoutManager = LinearLayoutManager(
                    this@HealthGoalPlanActivity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }
        }
    }

    override fun initListener() {
        binding.apply {
            btnBack.onClick {
                onBackPressed()
            }
            btnHealthGoalPlan.onClick {
                showCreateHealthGoalDialog()
            }
        }
    }

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    override fun initObservers() {
        viewModel.apply {
            observe(livePhysicalLevels) { physicals ->
                physicals?.let {
                    physicalLevels.clear()
                    physicalLevels.addAll(it)
                }
            }

            observe(liveHealthGoals) { goals ->
                goals?.let {
                    healthGoals.clear()
                    healthGoals.addAll(it)
                    healthGoalAdapter.notifyDataSetChanged()
                }
            }

            observe(liveCreateHealthGoal) { response ->
                if (response is ApiCommonResponse) {
                    val isSuccess = response.status

                    if (isSuccess) {
                        toastSuccess(response.data.value)
                        viewModel.fetchAllHealthGoals()
                    } else {
                        toastError(response.data.value)
                    }
                }
            }

            observeDistinct(liveIsLoading()) { isLoading ->
                showLoadingView(isLoading == true)
            }
        }
    }

    private fun showCreateHealthGoalDialog() {
        createHealthGoalDialog = CreateHealthGoalDialog.newInstance()
        createHealthGoalDialog?.let {
            if (!it.isAdded) {
                it.show(supportFragmentManager, CreateHealthGoalDialog.TAG)
            }
        }
    }

    private fun showLoadingView(isShow: Boolean) {
        binding.loading.apply {
            backgroundView.setVisible(false)
            progressBar.setVisible(isShow)
        }
    }

    private fun goToHealthGoalPlanDetail(healthGoal: HealthGoalDto) {
        HealthGoalPlanDetailActivity.startActivity(this) {
            putExtra(KEY_HEALTH_GOAL_PLAN, healthGoal)
        }
    }

    companion object {
        const val KEY_HEALTH_GOAL_PLAN = "key_health_goal_plan"

        fun startActivity(context: Context?, configIntent: Intent.() -> Unit) {
            context?.let {
                it.start<HealthGoalPlanActivity> {
                    apply(configIntent)
                }
            }
        }
    }
}
