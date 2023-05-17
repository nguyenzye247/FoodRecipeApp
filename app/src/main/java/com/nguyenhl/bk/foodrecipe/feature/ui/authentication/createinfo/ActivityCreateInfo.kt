package com.nguyenhl.bk.foodrecipe.feature.ui.authentication.createinfo

import androidx.activity.viewModels
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.nguyenhl.bk.foodrecipe.R
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.databinding.ActivityCreateAccountBinding
import com.nguyenhl.bk.foodrecipe.databinding.ActivityCreateInfoBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.ViewModelProviderFactory
import com.nguyenhl.bk.foodrecipe.feature.util.DateFormatUtil
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.redmadrobot.inputmask.helper.Mask


class ActivityCreateInfo : BaseActivity<ActivityCreateInfoBinding, CreateInfoViewModel>() {
    override fun getLazyBinding() = lazy { ActivityCreateInfoBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModels<CreateInfoViewModel> {
        ViewModelProviderFactory(BaseInput.CreateInfoInput(application))
    }

    override fun initViews() {
        adjustScreenSize(binding.btnBack)
    }

    override fun initListener() {
        binding.apply {
            etDobInput.onClick {
                showDatePicker()
            }
            tipGenderInput.setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newText ->
                
            }
            etHealthInput.onClick {
                //TODO: show health status selection dialog
            }
        }
    }

    override fun initObservers() {

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
}
