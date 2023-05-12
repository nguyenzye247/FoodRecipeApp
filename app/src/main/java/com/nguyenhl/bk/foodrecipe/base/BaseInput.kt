package com.nguyenhl.bk.foodrecipe.base

import android.app.Application

sealed class BaseInput {

    object NoInput : BaseInput()

    data class MainInput(
        val application: Application
    ) : BaseInput()
}
