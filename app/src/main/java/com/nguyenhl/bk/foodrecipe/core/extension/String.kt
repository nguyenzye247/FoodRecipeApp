package com.nguyenhl.bk.foodrecipe.core.extension

import android.os.Build
import android.text.Html
import android.text.Spanned
import com.wajahatkarim3.easyvalidation.core.view_ktx.minLength
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import java.util.regex.Pattern

internal fun String?.ifNotNullOrEmpty(action: (str: String) -> Unit) {
    if (!this.isNullOrEmpty()) {
        action(this)
    }
}

internal fun String.ifNotEmpty(action: (str: String) -> Unit) {
    if (this.isNotEmpty()) {
        action(this)
    }
}

internal fun String.isEmailValid(): Boolean {
    val regex =
        "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?!-)(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{1,6}$"
    val pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE)
    return pattern.matcher(this).matches()
}

internal fun String.fromHtml(): Spanned {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_COMPACT)
    } else {
        @Suppress("DEPRECATION")
        return Html.fromHtml(this)
    }
}



internal fun String.checkEmail(onInvalid: (message: String) -> Unit) {
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

internal fun String.checkPassword(onInvalid: (str: String) -> Unit) {
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
