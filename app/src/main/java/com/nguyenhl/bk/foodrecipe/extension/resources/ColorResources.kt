/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")
package com.nguyenhl.bk.foodrecipe.extension.resources


import android.content.Context
import android.content.res.ColorStateList
import android.os.Build.VERSION.SDK_INT
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.fragment.app.Fragment

/**
 * @see [androidx.core.content.ContextCompat.getColor]
 */
@ColorInt
fun Context.color(@ColorRes colorRes: Int): Int = if (SDK_INT >= 23) getColor(colorRes) else {
    @Suppress("DEPRECATION")
    resources.getColor(colorRes)
}

inline fun Fragment.color(@ColorRes colorRes: Int) = context!!.color(colorRes)
inline fun View.color(@ColorRes colorRes: Int) = context.color(colorRes)

/**
 * Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet).
 *
 * For theme dependent resources, the application theme will be implicitly used.
 */
//inline fun appColor(@ColorRes colorRes: Int) = appCtx.color(colorRes)


/**
 * @see [androidx.core.content.ContextCompat.getColorStateList]
 */
fun Context.colorSL(@ColorRes colorRes: Int): ColorStateList {
    return (if (SDK_INT >= 23) getColorStateList(colorRes) else {
        @Suppress("DEPRECATION")
        resources.getColorStateList(colorRes)
    })
}

inline fun Fragment.colorSL(@ColorRes colorRes: Int) = context!!.colorSL(colorRes)
inline fun View.colorSL(@ColorRes colorRes: Int) = context.colorSL(colorRes)

/**
 * Use this method for non configuration dependent resources when you don't have a [Context]
 * or when you're calling it from an Activity or a Fragment member (as the Context is not
 * initialized yet).
 *
 * For theme dependent resources, the application theme will be implicitly used.
 */
//inline fun appColorSL(@ColorRes colorRes: Int) = appCtx.colorSL(colorRes)