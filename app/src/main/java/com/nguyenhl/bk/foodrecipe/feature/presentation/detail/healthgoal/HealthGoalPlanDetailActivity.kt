package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.healthgoal

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RectShape
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IFillFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.dataprovider.LineDataProvider
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.renderer.LineChartRenderer
import com.nguyenhl.bk.foodrecipe.R
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observeDistinct
import com.nguyenhl.bk.foodrecipe.core.extension.parcelableExtra
import com.nguyenhl.bk.foodrecipe.core.extension.resources.txtString
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.toastError
import com.nguyenhl.bk.foodrecipe.core.extension.toastSuccess
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.core.extension.views.setVisible
import com.nguyenhl.bk.foodrecipe.databinding.ActivityHealthGoalDetailBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.dto.healthgoal.HealthGoalDetailDto
import com.nguyenhl.bk.foodrecipe.feature.dto.healthgoal.HealthIndicatorDto
import com.nguyenhl.bk.foodrecipe.feature.dto.healthgoal.MealSuggestRecipeDto
import com.nguyenhl.bk.foodrecipe.feature.dto.healthgoal.toRecipeDto
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.healthgoal.create.CreateHealthIndicatorDialog
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.RecipeDetailActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.RecipeDetailActivity.Companion.KEY_RECIPE_DTO
import com.nguyenhl.bk.foodrecipe.feature.presentation.healthgoal.HealthGoalPlanActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.healthgoal.create.CreateHealthGoalDialog
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.text.SimpleDateFormat
import java.util.*

class HealthGoalPlanDetailActivity :
    BaseActivity<ActivityHealthGoalDetailBinding, HealthGoalPlanDetailViewModel>() {
    private lateinit var suggestRecipeAdapter: HealthGoalMealSuggestRecipeAdapter
    private val todayIndicators: ArrayList<HealthIndicatorDto> = arrayListOf()

    private val weightEntries: ArrayList<Entry> = arrayListOf()
    private val heartRateEntries: ArrayList<Entry> = arrayListOf()
    private val bloodSugarEntries: ArrayList<Entry> = arrayListOf()

    private var createHealthIndicatorDialog: CreateHealthIndicatorDialog? = null

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
                showCreateHealthIndicatorDialog()
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
            observe(liveTodayHealthIndicator) { todayIdts ->
                todayIdts?.let {
                    todayIndicators.clear()
                    todayIndicators.addAll(todayIdts)
                }
            }
            observe(liveWeightIndicator) { weightIndicators ->
                weightIndicators?.let {
                    bindWeightIndicatorsViewData(weightIndicators)
                }
            }
            observe(liveHeartRateIndicator) { heartRateIndicators ->
                heartRateIndicators?.let {
                    bindHeartRateIndicatorsViewData(heartRateIndicators)
                }
            }
            observe(liveBloodSugarIndicator) { bloodSugarIndicators ->
                bloodSugarIndicators?.let {
                    bindBloodSugarIndicatorsViewData(bloodSugarIndicators)
                }
            }

            observe(liveCreateHealthIndicator) { response ->
                response ?: return@observe
                if (response.status) {
                    viewModel.fetchIndicators()
                    toastSuccess(response.data.value)
                } else {
                    toastError(response.data.value)
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

    private fun bindWeightIndicatorsViewData(indicators: List<HealthIndicatorDto>) {
        val entries = getChartData(indicators)
        val dataSet = createDataSet(entries, "Weight")

        val lineData = LineData(dataSet)
        lineData.setValueTextSize(DEFAULT_GRAPH_TEXT_SIZE)
        lineData.setValueTextColor(Color.BLACK)
        binding.lcWeight.apply {
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.granularity = 1f // Display one label per day
            xAxis.valueFormatter = DayAxisValueFormatter(entries, this)

            axisLeft.axisMinimum = getMinWeight(entries).minus(10f)
            axisLeft.axisMaximum = getMaxWeight(entries).plus(10f)

            data = lineData
            invalidate()
        }
    }

    private fun bindHeartRateIndicatorsViewData(indicators: List<HealthIndicatorDto>) {
        val entries = getChartData(indicators)
        val dataSet = createDataSet(entries, "Heart rate")

        val lineData = LineData(dataSet)
        lineData.setValueTextSize(DEFAULT_GRAPH_TEXT_SIZE)
        lineData.setValueTextColor(Color.BLACK)
        binding.lcHeartRate.apply {
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.granularity = 1f // Display one label per day
            xAxis.valueFormatter = DayAxisValueFormatter(entries, this)

            axisLeft.axisMinimum = getMinWeight(entries).minus(10f)
            axisLeft.axisMaximum = getMaxWeight(entries).plus(10f)

            data = lineData
            invalidate()
        }
    }

    private fun bindBloodSugarIndicatorsViewData(indicators: List<HealthIndicatorDto>) {
        val entries = getChartData(indicators)
        val dataSet = createDataSet(entries, "Blood sugar")

        val lineData = LineData(dataSet)
        lineData.setValueTextSize(DEFAULT_GRAPH_TEXT_SIZE)
        lineData.setValueTextColor(Color.BLACK)
        binding.lcBloodSugar.apply {
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.granularity = 1f // Display one label per day
            xAxis.valueFormatter = DayAxisValueFormatter(entries, this)

            axisLeft.axisMinimum = getMinWeight(entries).minus(10f)
            axisLeft.axisMaximum = getMaxWeight(entries).plus(10f)

            data = lineData
            invalidate()
        }
    }

    private fun getChartData(indicators: List<HealthIndicatorDto>): List<Entry> {
        val dataList = indicators.map {
            ChartData(it.value, it.createdAt)
        }.sortedBy { it.date }
        val entries = mutableListOf<Entry>()
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        dataList.forEach { entry ->
            val date = dateFormatter.parse(entry.date)
            val timestamp = date?.time?.toFloat() ?: 0f
            entries.add(Entry(timestamp, entry.value))
        }

        return entries
    }

    private fun createDataSet(entries: List<Entry>, dataType: String): LineDataSet {
        val dataSet = LineDataSet(entries, dataType)
        dataSet.color = Color.RED
        dataSet.setCircleColor(Color.RED)
        dataSet.lineWidth = 2f
        dataSet.circleRadius = 4f
        dataSet.setDrawCircleHole(false)
        dataSet.setDrawValues(false)

        dataSet.fillAlpha = 255
        dataSet.setDrawFilled(false)

        return dataSet
    }

    private fun showLoadingView(isShow: Boolean) {
        binding.loading.apply {
            backgroundView.setVisible(false)
            progressBar.setVisible(isShow)
            whiteBackgroundView.setVisible(isShow)
        }
    }

    private fun showCreateHealthIndicatorDialog() {
        createHealthIndicatorDialog = CreateHealthIndicatorDialog.newInstance()
        createHealthIndicatorDialog?.let {
            if (!it.isAdded) {
                it.show(supportFragmentManager, CreateHealthIndicatorDialog.TAG)
            }
        }
    }

    private fun goToRecipeDetail(recipe: MealSuggestRecipeDto) {
        RecipeDetailActivity.startActivity(this@HealthGoalPlanDetailActivity) {
            putExtra(KEY_RECIPE_DTO, recipe.toRecipeDto())
        }
    }

    data class ChartData(val value: Float, val date: String)

    private inner class DayAxisValueFormatter(private val entries: List<Entry>, val lineChart: LineChart) : ValueFormatter() {
        private val dateFormatter = SimpleDateFormat("MM/dd", Locale.US)
        private val indexValues = entries.map { it.x.toLong() }

        override fun getFormattedValue(value: Float): String {
            val index = indexValues.indexOf(value.toLong())
            if (index >= 0 && index < entries.size) {
                val entry = entries[index]
                val date = Date(entry.x.toLong())
                return dateFormatter.format(date)
            }
            return ""
        }
    }

    private fun getMinWeight(entries: List<Entry>): Float {
        return entries.minByOrNull { it.y }?.y ?: 0f
    }

    private fun getMaxWeight(entries: List<Entry>): Float {
        return entries.maxByOrNull { it.y }?.y ?: 0f
    }

    companion object {
        private const val DEFAULT_GRAPH_TEXT_SIZE = 14f

        fun startActivity(context: Context?, configIntent: Intent.() -> Unit) {
            context?.let {
                it.start<HealthGoalPlanDetailActivity> {
                    apply(configIntent)
                }
            }
        }
    }
}
