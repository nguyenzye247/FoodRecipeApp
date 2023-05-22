package com.nguyenhl.bk.foodrecipe.feature.presentation.authentication.login

import android.content.Context
import android.content.Intent
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.toast
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.core.extension.views.setError
import com.nguyenhl.bk.foodrecipe.core.extension.views.setVisible
import com.nguyenhl.bk.foodrecipe.databinding.ActivityLoginBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.presentation.authentication.createinfo.CreateInfoActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.authentication.forgot.ForgotPasswordActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.authentication.register.RegisterActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.dishprefered.DishPreferredActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.MainActivity
import com.nguyenhl.bk.foodrecipe.feature.util.checkEmail
import com.nguyenhl.bk.foodrecipe.feature.util.checkPassword
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
        viewModel.doOnUserLoggedIn {
            goToDishPreferred()
            finish()
        }
    }

    override fun initListener() {
        binding.apply {
            btnLogin.onClick {
                validateInputs { email, password ->
                    viewModel.setLoading(true)
                    viewModel.loginToAccount(email, password)
                }
            }
            tvForgotPassword.onClick {
                goToForgotPassword()
            }
            tvRegister.onClick {
                goToRegister()
            }
            btnBack.onClick {
                onBackPressed()
            }
        }
    }

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    override fun initObservers() {
        observe(viewModel.liveLoginStatus()) { loginStatus ->
            viewModel.setLoading(false)
            if (loginStatus == null) {
                return@observe
            }
            val status = loginStatus.status

            if (status) {
                toast("Login success")
                viewModel.checkForUserInfo {
                    // empty token
                    toast("Error on login, please try again")
                }
                return@observe
            }
        }
        observe(viewModel.liveIsLoading()) {
            binding.loading.progressBar.setVisible(it ?: false)
        }
        observe(viewModel.liveIsValidUserInfo()) { isValid ->
            isValid?.let {
                if (isValid) {
                    goToMain()
                } else {
                    goToDishPreferred()
                }
                finish()
            }
        }
    }

    private fun goToRegister() {
        RegisterActivity.startActivity(this) {
            // put stuffs
        }
    }

    private fun goToForgotPassword() {
        ForgotPasswordActivity.startActivity(this@LoginActivity) {
            // put stuffs
        }
    }

    private fun goToDishPreferred() {
        DishPreferredActivity.startActivity(this@LoginActivity) {
            // put stuffs
        }
    }

    private fun goToMain() {
        MainActivity.startActivity(this) {
            // put stuffs
        }
    }

    private fun validateInputs(
        onValid: (email: String, password: String) -> Unit
    ) {
        var isValid = true
        binding.apply {
            val emailInput = etEmail.text.toString()
            val passwordInput = etPassword.text.toString()

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
            if (isValid) {
                setAllInputValid()
                onValid(emailInput, passwordInput)
            }
        }
    }

    private fun setAllInputValid() {
        binding.apply {
            tipEmail.setError(false, null)
            tipPassword.setError(false, null)
        }
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
