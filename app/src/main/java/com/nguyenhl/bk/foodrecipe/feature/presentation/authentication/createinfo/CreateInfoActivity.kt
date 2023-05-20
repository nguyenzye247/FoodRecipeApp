package com.nguyenhl.bk.foodrecipe.feature.presentation.authentication.createinfo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.widget.TextView
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.view.OptionsPickerView
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
import com.nguyenhl.bk.foodrecipe.feature.util.DateFormatUtil
import com.skydoves.powerspinner.OnSpinnerDismissListener
import com.skydoves.powerspinner.PowerSpinnerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CreateInfoActivity : BaseActivity<ActivityCreateInfoBinding, CreateInfoViewModel>() {
    private var healthStatusPicker: OptionsPickerView<String>? = null
    private val healthStatuses: ArrayList<String> = arrayListOf()

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
                withContext(Dispatchers.Main) {
                    loadHealthStatusesToUI(healthStatuses)
                }
            }
        }
    }

    private fun loadHealthStatusesToUI(healthStatuses: ArrayList<HealthStatus>) {
        this.healthStatuses.clear()
        this.healthStatuses.add(txtString(R.string.none))
        this.healthStatuses.addAll(healthStatuses.map { it.name })
    }

    private fun showDatePicker() {

        //DatePicker
        TimePickerBuilder(this) { date, v -> //Callback
            val dateText = DateFormatUtil.formatSimpleDate(date)
            binding.etDobInput.setText(dateText)
        }
            .setSubCalSize(16)
            .isDialog(true)
            .setTitleBgColor(getColor(R.color.white))
            .setSubmitColor(getColor(R.color.rcp_blue_200))
            .setCancelColor(getColor(R.color.rcp_grey_100))
            .build()
            .show()
    }

    private fun showHealthStatusPicker() {
        healthStatusPicker =
            OptionsPickerBuilder(this@CreateInfoActivity) { oldIndex, oldItem, newIndex, newText ->
                if (healthStatuses.size > newIndex) {
                    setHeathStatusView(healthStatuses[oldIndex])
                }
            }.apply {
                setLayoutRes(R.layout.dialog_health_status_picker) { parentView ->
                    val btnApply: TextView = parentView.findViewById(R.id.btn_apply)
                    val btnCancel: TextView = parentView.findViewById(R.id.btn_cancel)
                    btnApply.onClick {
                        healthStatusPicker?.returnData()
                        healthStatusPicker?.dismiss()
                    }
                    btnCancel.onClick {
                        healthStatusPicker?.dismiss()
                    }
                }
                isDialog(true)
                setSelectOptions(0)
                setContentTextSize(16)
                isRestoreItem(true)
                setOutSideCancelable(false)
            }.build()
        healthStatusPicker?.apply {
            setPicker(healthStatuses)
        }?.show()
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
