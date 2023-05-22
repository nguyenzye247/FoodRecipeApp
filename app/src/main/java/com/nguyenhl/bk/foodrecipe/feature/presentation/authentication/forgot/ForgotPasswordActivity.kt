package com.nguyenhl.bk.foodrecipe.feature.presentation.authentication.forgot

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.databinding.ActivityForgotPasswordBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.ViewModelProviderFactory
import com.nguyenhl.bk.foodrecipe.feature.presentation.authentication.login.LoginActivity

class ForgotPasswordActivity: BaseActivity<ActivityForgotPasswordBinding, ForgotPasswordViewModel>(){
    override fun getLazyBinding() = lazy { ActivityForgotPasswordBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModels<ForgotPasswordViewModel> {
        ViewModelProviderFactory(BaseInput.ForgotPasswordInput(application))
    }

    override fun initViews() {
        binding.apply {
            btnSendToEmail.onClick {

            }
            btnBack.onClick {
                onBackPressed()
            }
        }
    }

    override fun initListener() {
        binding.apply {

        }
    }

    override fun initObservers() {

    }

    companion object {
        fun startActivity(context: Context?, configIntent: Intent.() -> Unit) {
            context?.let {
                it.start<LoginActivity> {
                    apply(configIntent)
                }
            }
        }
    }

}
