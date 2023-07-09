package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.healthgoal

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.nguyenhl.bk.foodrecipe.R
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observeDistinct
import com.nguyenhl.bk.foodrecipe.core.extension.parcelableExtra
import com.nguyenhl.bk.foodrecipe.core.extension.resources.txtString
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.core.extension.views.setVisible
import com.nguyenhl.bk.foodrecipe.databinding.ActivityHealthGoalDetailBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.dto.healthgoal.HealthGoalDetailDto
import com.nguyenhl.bk.foodrecipe.feature.dto.healthgoal.MealSuggestRecipeDto
import com.nguyenhl.bk.foodrecipe.feature.dto.healthgoal.toRecipeDto
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.RecipeDetailActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.RecipeDetailActivity.Companion.KEY_RECIPE_DTO
import com.nguyenhl.bk.foodrecipe.feature.presentation.healthgoal.HealthGoalPlanActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class HealthGoalPlanDetailActivity :
    BaseActivity<ActivityHealthGoalDetailBinding, HealthGoalPlanDetailViewModel>() {
    private lateinit var suggestRecipeAdapter: HealthGoalMealSuggestRecipeAdapter

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

            btnAddIndicator.onClick {

            }
        }
    }

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    override fun initObservers() {
        viewModel.apply {
            observe(liveHealthGoalDetail) { healthGoalDetail ->
                healthGoalDetail?.let {
                    bindHealthGoalDetailDataView(it)
                }
            }

            observeDistinct(liveIsLoading()) { isLoading ->
                showLoadingView(isLoading == true)
            }
        }
    }

    private fun bindHealthGoalDetailDataView(healthGoalDetail: HealthGoalDetailDto) {
        binding.apply {
            val startWeightValue = healthGoalDetail.currentWeight
            val targetWeightValue = healthGoalDetail.targetWeight
            if (startWeightValue < targetWeightValue) {
                tvHealthGoalTitle.text = txtString(R.string.lose_weight)
            } else {
                tvHealthGoalTitle.text = txtString(R.string.gain_weight)
            }

            startWeight.apply {
                tvStartWeightUnit.text = "kg"
                tvStartWeightValue.text = startWeightValue.toString()
            }
            targetWeight.apply {
                tvTargetWeightUnit.text = "kg"
                tvTargetWeightValue.text = targetWeightValue.toString()
            }
            calories.apply {
                tvDailyCaloriesValue.text = healthGoalDetail.dailyCalories.toString()
            }
            water.apply {
                tvDailyWaterUnit.text = "litters"
                tvDailyWaterValue.text = healthGoalDetail.dailyWater.toString()
            }
            dayGoal.apply {
                tvDayGoalUnit.text = "days"
                tvDayGoalValue.text = healthGoalDetail.dayGoal.toString()
            }
        }

        binding.rvDailySuggestions.apply {
            suggestRecipeAdapter =
                HealthGoalMealSuggestRecipeAdapter(healthGoalDetail.mealSuggests) { meal ->
                    val recipe = meal.recipe
                    goToRecipeDetail(recipe)
                }
            adapter = suggestRecipeAdapter
            layoutManager = LinearLayoutManager(
                this@HealthGoalPlanDetailActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
    }

    private fun showLoadingView(isShow: Boolean) {
        binding.loading.apply {
            backgroundView.setVisible(false)
            progressBar.setVisible(isShow)
            whiteBackgroundView.setVisible(isShow)
        }
    }

    private fun goToRecipeDetail(recipe: MealSuggestRecipeDto) {
        RecipeDetailActivity.startActivity(this@HealthGoalPlanDetailActivity) {
            putExtra(KEY_RECIPE_DTO, recipe.toRecipeDto())
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
