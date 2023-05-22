package com.nguyenhl.bk.foodrecipe.feature.base

import android.app.Application

sealed class BaseInput {

    object NoInput : BaseInput()

    data class MainInput(
        val application: Application
    ) : BaseInput()

    data class LoginInput(
        val application: Application
    ) : BaseInput()

    data class RegisterInput(
        val application: Application
    ) : BaseInput()

    data class ForgotPasswordInput(
        val application: Application
    ) : BaseInput()

    data class CreateAccountInput(
        val application: Application
    ) : BaseInput()

    data class DishPreferredInput(
        val application: Application
    ) : BaseInput()

    data class CreateInfoInput(
        val application: Application
    ) : BaseInput()
}
