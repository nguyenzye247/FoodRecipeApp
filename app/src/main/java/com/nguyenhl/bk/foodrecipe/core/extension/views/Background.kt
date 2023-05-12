/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")

package com.nguyenhl.bk.foodrecipe.core.extension.views

import android.graphics.drawable.Drawable
import android.os.Build.VERSION.SDK_INT
import android.view.View
import androidx.annotation.ColorInt

/**
 * This View's background [Drawable].
 *
 * This is an alias to the `background` property that is backwards compatible below API 16.
 */
inline var View.bg: Drawable?
    get() = background
    @Suppress("DEPRECATION")
    set(value) = if (SDK_INT < 16) setBackgroundDrawable(value) else background = value

/**
 * Set only property that takes a color int.
 *
 * This is an alias to [View.setBackgroundColor].
 */
inline var View.backgroundColor: Int
    @Deprecated(NO_GETTER, level = DeprecationLevel.HIDDEN) get() = noGetter
    set(@ColorInt colorInt) = setBackgroundColor(colorInt)
