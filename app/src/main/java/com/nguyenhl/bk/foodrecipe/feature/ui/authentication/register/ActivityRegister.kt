package com.nguyenhl.bk.foodrecipe.feature.ui.authentication.register

import androidx.activity.viewModels
import com.nguyenhl.bk.foodrecipe.databinding.ActivityRegisterBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.ViewModelProviderFactory

class ActivityRegister : BaseActivity<ActivityRegisterBinding, RegisterViewModel>() {
    override fun getLazyBinding() = lazy { ActivityRegisterBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModels<RegisterViewModel> {
        ViewModelProviderFactory(BaseInput.RegisterInput(application))
    }

    override fun initViews() {

    }

    override fun initListener() {

    }

    override fun initObservers() {

    }
}
