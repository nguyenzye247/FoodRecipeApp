package com.nguyenhl.bk.foodrecipe.feature.ui.authentication.forgot

import androidx.activity.viewModels
import com.nguyenhl.bk.foodrecipe.databinding.ActivityForgotPasswordBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.ViewModelProviderFactory

class ForgotPasswordActivity: BaseActivity<ActivityForgotPasswordBinding, ForgotPasswordViewModel>(){
    override fun getLazyBinding() = lazy { ActivityForgotPasswordBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModels<ForgotPasswordViewModel> {
        ViewModelProviderFactory(BaseInput.ForgotPasswordInput(application))
    }

    override fun initViews() {

    }

    override fun initListener() {

    }

    override fun initObservers() {

    }

}
