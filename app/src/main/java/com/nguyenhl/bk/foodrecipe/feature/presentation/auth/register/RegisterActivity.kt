package com.nguyenhl.bk.foodrecipe.feature.presentation.auth.register

import android.content.Context
import android.content.Intent
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observeDistinct
import com.nguyenhl.bk.foodrecipe.core.extension.longToast
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.core.extension.views.setError
import com.nguyenhl.bk.foodrecipe.core.extension.views.setVisible
import com.nguyenhl.bk.foodrecipe.databinding.ActivityRegisterBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.presentation.auth.forgot.ForgotPasswordActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.auth.login.LoginActivity
import com.nguyenhl.bk.foodrecipe.feature.util.checkEmail
import com.nguyenhl.bk.foodrecipe.feature.util.checkPassword
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>() {
    val e = "lenguyen2470@gmail.com"
    val p = "11111111"

    override fun getLazyBinding() = lazy { ActivityRegisterBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModel<RegisterViewModel> {
        parametersOf(BaseInput.RegisterInput(application))
    }

    override fun initViews() {
        binding.apply {

        }
    }

    override fun initListener() {
        binding.apply {
            btnRegister.onClick {
                validateInputs { email, password, confirmedPassword ->
                    viewModel.setLoading(true)
                    viewModel.registerNewAccount(email, password, confirmedPassword)
                }
            }
            btnBack.onClick {
                onBackPressed()
            }
            tvLogin.onClick {
                goToLogin()
            }
            tvForgotPassword.onClick {
                goToForgotPassword()
            }
        }
    }

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    override fun initObservers() {
        observeDistinct(viewModel.liveIsLoading()) {
            showLoadingView(it ?: false)
        }
        observe(viewModel.liveRegisterStatus()) { registerStatus ->
            viewModel.setLoading(false)

            if (registerStatus == null) return@observe
            val message = registerStatus.data.value
            val status = registerStatus.status

            longToast(message)
            if (status) {
                goToLogin()
                return@observe
            }
        }
    }

    private fun goToLogin() {
        LoginActivity.startActivity(this) {
            // put stuffs

        }
    }

    private fun goToForgotPassword() {
        ForgotPasswordActivity.startActivity(this@RegisterActivity) {
            // put stuffs
        }
    }

    private fun validateInputs(
        onValid: (email: String, password: String, confirmedPassword: String) -> Unit
    ) {
        var isValid = true
        binding.apply {
            val emailInput = etEmail.text.toString()
            val passwordInput = etPassword.text.toString()
            val confirmedPasswordInput = etConfirmPassword.text.toString()

            emailInput.checkEmail { errorMessage ->
                isValid = false
                etEmail.setError(true)
                tipEmail.setError(true, errorMessage)
            }
            passwordInput.checkPassword { errorMessage ->
                isValid = false
                etPassword.setError(true)
                tipPassword.setError(true, errorMessage)
            }
            confirmedPasswordInput.checkPassword { errorMessage ->
                isValid = false
                etConfirmPassword.setError(true)
                tipConfirmPassword.setError(true, errorMessage)
            }
            if (passwordInput != confirmedPasswordInput) {
                isValid = false
                etConfirmPassword.setError(true)
                tipConfirmPassword.setError(
                    true,
                    "Confirmed password must be matched with password"
                )
            }
            if (isValid) {
                setAllInputValid()
                onValid(emailInput, passwordInput, confirmedPasswordInput)
            }
        }
    }

    private fun setAllInputValid() {
        binding.apply {
            tipEmail.setError(false, null)
            tipPassword.setError(false, null)
            tipConfirmPassword.setError(false, null)
        }
    }

    private fun showLoadingView(isShow: Boolean) {
        binding.loading.progressBar.setVisible(isShow)
    }

    companion object {
        fun startActivity(context: Context?, configIntent: Intent.() -> Unit) {
            context?.let {
                it.start<RegisterActivity> {
                    apply(configIntent)
                }
            }
        }
    }
}
