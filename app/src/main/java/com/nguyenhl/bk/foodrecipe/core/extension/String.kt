package com.nguyenhl.bk.foodrecipe.core.extension

import android.os.Build
import android.text.Html
import android.text.Spanned
import java.util.regex.Pattern

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
