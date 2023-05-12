package com.nguyenhl.bk.foodrecipe.core.extension.views

import android.os.Build.VERSION.SDK_INT
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
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
