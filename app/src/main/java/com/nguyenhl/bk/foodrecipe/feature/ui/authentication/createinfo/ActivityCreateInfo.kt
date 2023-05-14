package com.nguyenhl.bk.foodrecipe.feature.ui.authentication.createinfo

import androidx.activity.viewModels
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.nguyenhl.bk.foodrecipe.databinding.ActivityCreateAccountBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.ViewModelProviderFactory


class ActivityCreateInfo : BaseActivity<ActivityCreateAccountBinding, CreateInfoViewModel>() {
    override fun getLazyBinding() = lazy { ActivityCreateAccountBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModels<CreateInfoViewModel> {
        ViewModelProviderFactory(BaseInput.CreateInfoInput(application))
    }

    override fun initViews() {
        //TimePicker
        TimePickerBuilder(this) { date, v -> //Callback
//            tvTime.setText(getTime(date))
        }
            .build()
            .show()
    }

    override fun initListener() {

    }

    override fun initObservers() {

    }
}
