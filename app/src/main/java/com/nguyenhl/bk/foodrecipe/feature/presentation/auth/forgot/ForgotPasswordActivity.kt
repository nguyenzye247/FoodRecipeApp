package com.nguyenhl.bk.foodrecipe.feature.presentation.auth.forgot

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observeDistinct
import com.nguyenhl.bk.foodrecipe.core.extension.longToast
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.toast
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.core.extension.views.setError
import com.nguyenhl.bk.foodrecipe.core.extension.views.setVisible
import com.nguyenhl.bk.foodrecipe.databinding.ActivityForgotPasswordBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.ViewModelProviderFactory
import com.nguyenhl.bk.foodrecipe.feature.presentation.auth.login.LoginActivity
import com.nguyenhl.bk.foodrecipe.feature.util.checkEmail

class ForgotPasswordActivity: BaseActivity<ActivityForgotPasswordBinding, ForgotPasswordViewModel>(){
    override fun getLazyBinding() = lazy { ActivityForgotPasswordBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModels<ForgotPasswordViewModel> {
        ViewModelProviderFactory(BaseInput.ForgotPasswordInput(application))
    }

    override fun initViews() {
        binding.apply {
            btnSendToEmail.onClick {
                validateInputs { email ->
                    viewModel.sendForgotPassword(email)
                }
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

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    override fun initObservers() {
        observeDistinct(viewModel.liveIsLoading()) { isLoading ->
            showLoadingView(isLoading ?: false)
        }

        observe(viewModel.liveForgotPasswordStatus()) { forgotPasswordStatus ->
            viewModel.setLoading(false)
            if (forgotPasswordStatus == null) {
                return@observe
            }
            val status = forgotPasswordStatus.status
            val message = forgotPasswordStatus.data.value

            longToast(message)
        }
    }

    private fun validateInputs(
        onValid: (email: String) -> Unit
    ) {
        var isValid = true
        binding.apply {
            val emailInput = etEmail.text.toString()
            emailInput.checkEmail { errorMessage ->
                isValid = false
                etEmail.setError(true)
                tipEmail.setError(true, errorMessage)
            }
            if (isValid) {
                setAllInputValid()
                onValid(emailInput)
            }
        }
    }

    private fun setAllInputValid() {
        binding.apply {
            tipEmail.setError(false, null)
        }
    }

    private fun showLoadingView(isShow: Boolean) {
        binding.loading.progressBar.setVisible(isShow)
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
