package com.nguyenhl.bk.foodrecipe.feature.presentation.authentication.login

import com.nguyenhl.bk.foodrecipe.databinding.ActivityLoginBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    val e = "lenguyen2470@gmail.com"
    val p = "11111111"

    override fun getLazyBinding() = lazy { ActivityLoginBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModel<LoginViewModel> {
        parametersOf(BaseInput.LoginInput(application))
    }

    override fun initViews() {

    }

    override fun initListener() {

    }

    override fun initObservers() {

    }
}
