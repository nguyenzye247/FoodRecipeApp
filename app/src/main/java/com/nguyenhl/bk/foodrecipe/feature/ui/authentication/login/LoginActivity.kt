package com.nguyenhl.bk.foodrecipe.feature.ui.authentication.login

import androidx.activity.viewModels
import com.nguyenhl.bk.foodrecipe.databinding.ActivityLoginBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity

class LoginActivity: BaseActivity<ActivityLoginBinding, LoginViewModel>() {
    override fun getLazyBinding() = lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModels<LoginViewModel>()

    override fun initViews() {

    }

    override fun initListener() {

    }

    override fun initObservers() {

    }
}
