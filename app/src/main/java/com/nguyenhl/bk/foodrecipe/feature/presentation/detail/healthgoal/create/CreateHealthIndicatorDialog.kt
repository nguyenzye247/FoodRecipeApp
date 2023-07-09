package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.healthgoal.create

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.toastError
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.databinding.DialogAddHealthIndicatorBinding
import com.nguyenhl.bk.foodrecipe.feature.dto.healthgoal.HealthIndicatorDto
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.healthgoal.HealthGoalPlanDetailViewModel

class CreateHealthIndicatorDialog : DialogFragment() {
    private val binding by lazy { DialogAddHealthIndicatorBinding.inflate(layoutInflater) }
    private val viewModel by lazy { activityViewModels<HealthGoalPlanDetailViewModel>().value }

    companion object {
        const val TAG = "Tag_CreateHealthIndicatorDialog"

        fun newInstance(): CreateHealthIndicatorDialog = CreateHealthIndicatorDialog()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {
        dialog?.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
            )
        }

        binding.apply {
            btnCreateHealthIndicator.isEnabled = false
        }
    }

    private fun initListeners() {
        binding.apply {
            btnCreateHealthIndicator.onClick {
                createHealthIndicator()
                dismiss()
            }
        }
    }

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    private fun initObservers() {
        viewModel.apply {
            observe(liveTodayHealthIndicator) { todayIndicators ->
                binding.btnCreateHealthIndicator.isEnabled = true
                todayIndicators?.let {
                    bindHealthIndicatorDataView(it)
                }
            }
        }
    }

    private fun bindHealthIndicatorDataView(healthIndicators: List<HealthIndicatorDto>) {
        binding.apply {
            etCurrentWeight.apply {
                val todayWeight = healthIndicators.firstOrNull { it.type == "weight" }
                todayWeight?.let {
                    setText(it.value.toString())
                    isEnabled = false
                }
            }

            etHeartRate.apply {
                val todayHeartRate = healthIndicators.firstOrNull { it.type == "heartrate" }
                todayHeartRate?.let {
                    setText(it.value.toString())
                    isEnabled = false
                }
            }

            etBloodSugar.apply {
                val todayBloodSugar = healthIndicators.firstOrNull { it.type == "bloodsugar" }
                todayBloodSugar?.let {
                    setText(it.value.toString())
                    isEnabled = false
                }
            }
        }
    }

    private fun createHealthIndicator() {
        kotlin.runCatching {
            val currentWeight = binding.etCurrentWeight.text.toString()
            val heartRate = binding.etHeartRate.text.toString()
            val bloodSugar = binding.etBloodSugar.text.toString()

            viewModel.createHealthIndicator(
                currentWeight.toFloat(),
                heartRate.toInt(),
                bloodSugar.toInt()
            )
        }.getOrElse {
            requireContext().toastError("Wrong format!")
        }
    }

}
