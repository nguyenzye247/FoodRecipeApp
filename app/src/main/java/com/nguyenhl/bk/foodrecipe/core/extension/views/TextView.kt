package com.nguyenhl.bk.foodrecipe.core.extension.views

import android.os.Build.VERSION.SDK_INT
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import com.nguyenhl.bk.foodrecipe.R
import com.nguyenhl.bk.foodrecipe.core.extension.resources.getColor

inline var TextView.textResource: Int
    @Deprecated(NO_GETTER, level = DeprecationLevel.HIDDEN) get() = noGetter
    set(@StringRes value) = setText(value)

inline var TextView.textColorResource: Int
    @Deprecated(NO_GETTER, level = DeprecationLevel.HIDDEN) get() = noGetter
    set(@ColorRes value) = setTextColor(getColor(value))

var TextView.textAppearance: Int
    @Deprecated(NO_GETTER, level = DeprecationLevel.HIDDEN) get() = noGetter
    @Suppress("DEPRECATION")
    set(@StyleRes value) = if (SDK_INT < 23) setTextAppearance(context, value)
    else setTextAppearance(value)

fun TextView.setTextIncludeDigit(line: String) {
    val spannableString = SpannableString(line)
    val numberRegex = "\\b\\d+(g|oz|l|ml|kg|gal|C)?\\b".toRegex() // Regex to match numbers

    val matches = numberRegex.findAll(line)
    for (match in matches) {
        val start = match.range.first
        val end = match.range.last + 1
        spannableString.setSpan(
            ForegroundColorSpan(this.getColor(R.color.rcp_blue_200)), start, end,
            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }

    this.text = spannableString
}
