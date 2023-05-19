package com.nguyenhl.bk.foodrecipe.feature.presentation.authentication.register

import com.nguyenhl.bk.foodrecipe.core.extension.toast
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.core.extension.views.setError
import com.nguyenhl.bk.foodrecipe.databinding.ActivityRegisterBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.wajahatkarim3.easyvalidation.core.view_ktx.minLength
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ActivityRegister : BaseActivity<ActivityRegisterBinding, RegisterViewModel>() {
    override fun getLazyBinding() = lazy { ActivityRegisterBinding.inflate(layoutInflater) }

//    override fun getLazyViewModel() = viewModels<RegisterViewModel> {
//        ViewModelProviderFactory(BaseInput.RegisterInput(application))
//    }

    override fun getLazyViewModel() = viewModel<RegisterViewModel> {
        parametersOf(BaseInput.RegisterInput(application))
    }

    override fun initViews() {

    }

    override fun initListener() {
        binding.apply {
            btnRegister.onClick {
                val e = "levanhuy94pbc@gmail.com"
                val p = "12345678"
                validateInputs { email, password, confirmedPassword ->
                    viewModel.registerNewAccount(email, password, confirmedPassword)
                }
            }
        }
    }

    override fun initObservers() {
        viewModel.liveRegisterStatus().observe(this@ActivityRegister) { registerStatus ->
            if (registerStatus == null) return@observe
            val message = registerStatus.first
            val status = registerStatus.second

            toast(message)
            if (status) {
                goToLogin()
                return@observe
            }
        }
    }

    private fun goToLogin() {

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

    private fun String.checkEmail(onInvalid: (message: String) -> Unit) {
        var isValid = true
        var message = ""
        nonEmpty {
            // error
            isValid = false
            message = "Field can not be empty"
        }
        validEmail {
            // error
            isValid = false
            message = "Wrong email format"
        }
        if (!isValid) {
            onInvalid(message)
        }
    }

    private fun String.checkPassword(onInvalid: (str: String) -> Unit) {
        var isValid = true
        var message = ""
        nonEmpty {
            // error
            isValid = false
            message = "Field can not be empty"
        }
        minLength(8) {
            // error
            isValid = false
            message = "Password length must be greater than 8"
        }
        if (!isValid) {
            onInvalid(message)
            return
        }
    }

    private fun setAllInputValid() {
        binding.apply {
            tipEmail.setError(false, null)
            tipPassword.setError(false, null)
            tipConfirmPassword.setError(false, null)
        }
    }
}
