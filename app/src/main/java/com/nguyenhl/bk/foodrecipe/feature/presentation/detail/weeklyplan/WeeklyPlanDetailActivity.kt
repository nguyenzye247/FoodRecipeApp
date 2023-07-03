package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.weeklyplan

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observeDistinct
import com.nguyenhl.bk.foodrecipe.core.extension.parcelableExtra
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.views.loadImage
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.core.extension.views.setVisible
import com.nguyenhl.bk.foodrecipe.databinding.ActivityWeeklyPlanDetailBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.dto.enumdata.WeekDay
import com.nguyenhl.bk.foodrecipe.feature.dto.weeklyplan.WeeklyPlanDto
import com.nguyenhl.bk.foodrecipe.feature.dto.weeklyplan.detail.WeeklyPlanDetailDto
import com.nguyenhl.bk.foodrecipe.feature.dto.weeklyplan.detail.WeeklyPlanItemDto
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class WeeklyPlanDetailActivity :
    BaseActivity<ActivityWeeklyPlanDetailBinding, WeeklyPlanDetailViewModel>() {
    private lateinit var weekDayAdapter: WeekDayAdapter
    private val weeklyPlanItem: ArrayList<WeeklyPlanItemDto> = arrayListOf()

    private val weeklyPlanDto by lazy { intent.parcelableExtra<WeeklyPlanDto>(KEY_WEEKLY_PLAN_DETAIL_DTO) }

    override fun getLazyBinding() = lazy { ActivityWeeklyPlanDetailBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModel<WeeklyPlanDetailViewModel> {
        parametersOf(
            BaseInput.WeeklyPlanDetailInput(
                application,
                intent.parcelableExtra(KEY_WEEKLY_PLAN_DETAIL_DTO)
            )
        )
    }

    override fun initViews() {
        val weekdayItems = enumValues<WeekDay>().toList().apply {
            first().apply { isSelected = true }
        }
        weekDayAdapter = WeekDayAdapter(weekdayItems) { weekday ->
            weekDayAdapter.notifyDataSetChanged()
            //
        }
        binding.apply {
            rvWeekDay.apply {
                adapter = weekDayAdapter
                layoutManager = LinearLayoutManager(
                    this@WeeklyPlanDetailActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            }
            weeklyPlanDto?.let {
                tvWeeklyPlanTitle.text = it.name
            }
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
            observe(liveWeeklyPlanDetail()) { weeklyPlanDetails ->
                weeklyPlanDetails?.let {
                    bindingWeeklyPlanDetailViewData(it)
                }
            }

            observeDistinct(liveIsLoading()) { isLoading ->
                showLoadingView(isLoading == true)
            }
        }
    }

    private fun bindingWeeklyPlanDetailViewData(weeklyPlanDetail: WeeklyPlanDetailDto) {
        binding.apply {
            weeklyPlanItem.clear()
            weeklyPlanItem.addAll(weeklyPlanDetail.weeklyPlans)

            val mondayItem = weeklyPlanItem.groupBy { it.weekDay }["Monday"] ?: return
            ivPlanBreakfast.loadImage(mondayItem[0].meal.recipe.imageUrl)
            ivPlanLunch.loadImage(mondayItem[1].meal.recipe.imageUrl)
            ivPlanDinner.loadImage(mondayItem[2].meal.recipe.imageUrl)
        }
    }

    private fun showLoadingView(isShow: Boolean) {
        binding.loading.apply {
            backgroundView.setVisible(false)
            progressBar.setVisible(isShow)
        }
    }

    companion object {
        const val KEY_WEEKLY_PLAN_DETAIL_DTO = "key_weekly_plan_detail_dto"

        fun startActivity(context: Context?, configIntent: Intent.() -> Unit) {
            context?.let {
                it.start<WeeklyPlanDetailActivity> {
                    apply(configIntent)
                }
            }
        }
    }
}
