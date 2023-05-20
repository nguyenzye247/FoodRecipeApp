package com.nguyenhl.bk.foodrecipe.feature.presentation.authentication.createinfo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bigkoo.pickerview.view.OptionsPickerView
import com.bigkoo.pickerview.view.TimePickerView
import com.nguyenhl.bk.foodrecipe.R
import com.nguyenhl.bk.foodrecipe.core.extension.resources.txtString
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.views.bg
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.databinding.ActivityCreateInfoBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.ViewModelProviderFactory
import com.nguyenhl.bk.foodrecipe.feature.data.datasource.database.model.HealthStatus
import com.nguyenhl.bk.foodrecipe.feature.dto.HealthStatusDto
import com.nguyenhl.bk.foodrecipe.feature.util.DateFormatUtil
import com.nguyenhl.bk.foodrecipe.feature.util.DialogUtil
import com.skydoves.powerspinner.OnSpinnerDismissListener
import com.skydoves.powerspinner.PowerSpinnerView
import kotlinx.coroutines.launch


class CreateInfoActivity : BaseActivity<ActivityCreateInfoBinding, CreateInfoViewModel>() {
    private var datePicker: TimePickerView? = null
    private var healthStatusPicker: OptionsPickerView<HealthStatusDto>? = null
    private val healthStatuses: ArrayList<HealthStatusDto> = arrayListOf()

    override fun getLazyBinding() = lazy { ActivityCreateInfoBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModels<CreateInfoViewModel> {
        ViewModelProviderFactory(BaseInput.CreateInfoInput(application))
    }

    override fun initViews() {
        adjustScreenSize(binding.btnBack)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun initListener() {
        binding.apply {
            btnContinue.onClick {

            }
            etDobInput.onClick {
                showDatePicker()
            }
            tipGenderInput.apply {
                setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newText ->
                    tipGenderInput.setInputBg()
                }
                onSpinnerDismissListener = OnSpinnerDismissListener {
                    tipGenderInput.setInputBg()
                }
            }
            etHealthInput.onClick {
                showHealthStatusPicker()
            }
            btnBack.onClick {

            }
        }
    }

    override fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                val healthStatuses = viewModel.getAllDbHealthStatuses()
                loadHealthStatusesToUI(healthStatuses)
            }
        }
    }

    private fun loadHealthStatusesToUI(healthStatuses: ArrayList<HealthStatus>) {
        this.healthStatuses.clear()
        this.healthStatuses.add(HealthStatusDto("", txtString(R.string.none)))
        this.healthStatuses.addAll(healthStatuses.map {
            HealthStatusDto(
                it.idHealthStatus,
                it.name
            )
        })
    }

    private fun showDatePicker() {
        //DatePicker
        datePicker = DialogUtil.buildDatePicker(
            this@CreateInfoActivity,
            onApply = {
                datePicker?.returnData()
                datePicker?.dismiss()
            },
            onCancel = {
                datePicker?.dismiss()
            }
        ) { date ->
            val dateText = DateFormatUtil.formatSimpleDate(date)
            binding.etDobInput.setText(dateText)
        }
        datePicker?.show()
    }

    private fun showHealthStatusPicker() {
        healthStatusPicker = DialogUtil.buildHealthStatusPicker(
            this@CreateInfoActivity,
            onApply = {
                healthStatusPicker?.returnData()
                healthStatusPicker?.dismiss()
            },
            onCancel = {
                healthStatusPicker?.dismiss()
            }
        ) { index ->
            if (healthStatuses.size > index) {
                setHeathStatusView(healthStatuses[index].name)
            }
        }
        healthStatusPicker?.apply {
            setPicker(healthStatuses)
        }?.show()
    }

    private fun validateInputs(
        onValid: (email: String, password: String) -> Unit
    ) {

    }

    private fun setHeathStatusView(healthStatusName: String) {
        binding.etHealthInput.setText(healthStatusName)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun PowerSpinnerView.setInputBg() {
        bg = getDrawable(R.drawable.bg_app_input)
    }

    companion object {
        fun startActivity(context: Context?, configIntent: Intent.() -> Unit) {
            context?.let {
                it.start<CreateInfoActivity> {
                    apply(configIntent)
                }
            }
        }
    }
}
