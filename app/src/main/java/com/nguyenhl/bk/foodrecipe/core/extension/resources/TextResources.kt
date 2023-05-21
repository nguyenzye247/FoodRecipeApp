/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")

package com.nguyenhl.bk.foodrecipe.core.extension.resources

import android.content.Context
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

inline fun Context.txt(@StringRes stringResId: Int): CharSequence = resources.getText(stringResId)
inline fun Context.txtString(@StringRes stringResId: Int): String = resources.getText(stringResId).toString()
inline fun Fragment.txt(@StringRes stringResId: Int) = context!!.txt(stringResId)
inline fun View.txt(@StringRes stringResId: Int) = context.txt(stringResId)
