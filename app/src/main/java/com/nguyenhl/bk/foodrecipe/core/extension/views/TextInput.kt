package com.nguyenhl.bk.foodrecipe.core.extension.views

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.nguyenhl.bk.foodrecipe.R
import com.nguyenhl.bk.foodrecipe.core.extension.resources.getColor

fun TextInputLayout.setError(isError: Boolean, errorText: String?) {
    this.errorContentDescription = errorText
    this.isErrorEnabled = isError
    this.error = errorText
    this.boxBackgroundColor = if (isError)
        this.getColor(R.color.rcp_red_300)
    else
        this.getColor(R.color.rcp_blue_100)
}

fun TextInputEditText.setError(isError: Boolean) {
    val textColor = if (isError)
        this.getColor(R.color.rcp_red_100)
    else
        this.getColor(R.color.black)
    this.setTextColor(textColor)
}
