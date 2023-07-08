package com.nguyenhl.bk.foodrecipe.feature.presentation.healthgoal.create

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.bigkoo.pickerview.view.OptionsPickerView
import com.nguyenhl.bk.foodrecipe.core.extension.toastError
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.databinding.DialogCreateHealthGoalBinding
import com.nguyenhl.bk.foodrecipe.feature.dto.HealthStatusDto
import com.nguyenhl.bk.foodrecipe.feature.dto.healthgoal.PhysicalLevelDto
import com.nguyenhl.bk.foodrecipe.feature.presentation.healthgoal.HealthGoalPlanViewModel
import com.nguyenhl.bk.foodrecipe.feature.util.DialogUtil

class CreateHealthGoalDialog: DialogFragment() {
    private val binding by lazy { DialogCreateHealthGoalBinding.inflate(layoutInflater) }
    private val viewModel by lazy { activityViewModels<HealthGoalPlanViewModel>().value }
    private var onCreateClick: () -> Unit = {}
    private var physicalLevelPicker: OptionsPickerView<PhysicalLevelDto>? = null

    companion object {
        const val TAG = "Tag_CreateHealthGoalDialog"

        fun newInstance(): CreateHealthGoalDialog = CreateHealthGoalDialog()
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

        }
    }

    private fun initListeners() {
        binding.apply {
            btnCreateHealthGoal.onClick {
                createHealthGoal()
                dismiss()
            }

            etPhysicalLevel.onClick {
                showPhysicalLevelPicker()
            }
        }
    }

    private fun showPhysicalLevelPicker() {
        physicalLevelPicker = DialogUtil.buildPhysicalLevelPicker(
            requireContext(),
            onCreate = {
                physicalLevelPicker?.returnData()
                physicalLevelPicker?.dismiss()
            },
            onOptionSelectListener = { index ->
                if (viewModel.physicalLevels.size > index) {
                    viewModel.selectedPhysicalLevel = viewModel.physicalLevels[index]
                    setPhysicalLevelView(viewModel.physicalLevels[index].name)
                }
            }
        )
        physicalLevelPicker?.apply {
            setPicker(viewModel.physicalLevels)
        }?.show()
    }

    private fun setPhysicalLevelView(physicalLevelName: String) {
        binding.etPhysicalLevel.setText(physicalLevelName)
    }

    private fun createHealthGoal() {
        kotlin.runCatching {
            val targetWeight = binding.etTargetWeight.text.toString()
            val dayGoal = binding.etDayGoal.text.toString()

            viewModel.selectedPhysicalLevel?.idPhysicalLevel?.let { idPhysicalLevel ->
                viewModel.createHealthGoal(
                    targetWeight.toFloat(),
                    dayGoal.toInt(),
                    idPhysicalLevel
                )
            }
        }.getOrElse {
            requireContext().toastError("Wrong format!")
        }
    }
}
