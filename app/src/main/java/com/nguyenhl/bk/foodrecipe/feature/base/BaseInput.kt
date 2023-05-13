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
}
