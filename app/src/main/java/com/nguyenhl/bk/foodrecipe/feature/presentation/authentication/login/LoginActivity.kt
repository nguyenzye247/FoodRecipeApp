package com.nguyenhl.bk.foodrecipe.feature.presentation.authentication.login

import androidx.activity.viewModels
import com.nguyenhl.bk.foodrecipe.databinding.ActivityLoginBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.ViewModelProviderFactory

class LoginActivity: BaseActivity<ActivityLoginBinding, LoginViewModel>() {
    override fun getLazyBinding() = lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModels<LoginViewModel> {
        ViewModelProviderFactory(BaseInput.LoginInput(application))
    }

    override fun initViews() {

    }

    override fun initListener() {

    }

    override fun initObservers() {

    }
}
