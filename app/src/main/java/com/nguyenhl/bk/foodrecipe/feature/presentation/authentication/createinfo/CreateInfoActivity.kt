package com.nguyenhl.bk.foodrecipe.feature.presentation.authentication.createinfo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.nguyenhl.bk.foodrecipe.R
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.views.bg
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.databinding.ActivityCreateInfoBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.ViewModelProviderFactory
import com.nguyenhl.bk.foodrecipe.feature.util.DateFormatUtil
import com.skydoves.powerspinner.IconSpinnerAdapter
import com.skydoves.powerspinner.IconSpinnerItem
import com.skydoves.powerspinner.OnSpinnerDismissListener
import com.skydoves.powerspinner.PowerSpinnerView


class CreateInfoActivity : BaseActivity<ActivityCreateInfoBinding, CreateInfoViewModel>() {
    override fun getLazyBinding() = lazy { ActivityCreateInfoBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModels<CreateInfoViewModel> {
        ViewModelProviderFactory(BaseInput.CreateInfoInput(application))
    }

    override fun initViews() {
        viewModel.getAllHealthStatuses()
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
            tipHealthInput.apply {

            }
            btnBack.onClick {

            }
        }
    }

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    override fun initObservers() {
        observe(viewModel.liveHealthStatuses()) { healthStatusResponse ->
            if (healthStatusResponse == null) return@observe

        }
    }

    private fun showDatePicker() {

        //DatePicker
        TimePickerBuilder(this) { date, v -> //Callback
            val dateText = DateFormatUtil.formatSimpleDate(date)
            binding.etDobInput.setText(dateText)
        }
            .setSubCalSize(16)
            .isDialog(true)
            .setTitleBgColor(getColor(R.color.rcp_blue_300))
            .setSubmitColor(getColor(R.color.rcp_blue_200))
            .setCancelColor(getColor(R.color.rcp_grey_100))
            .build()
            .show()
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
