@file:Suppress("NOTHING_TO_INLINE")

package com.nguyenhl.bk.foodrecipe.core.extension.resources

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Context.drawable(@DrawableRes drawableRes: Int): Drawable? =
    ContextCompat.getDrawable(this, drawableRes)

inline fun Fragment.drawable(@DrawableRes drawableRes: Int): Drawable? =
    context!!.drawable(drawableRes)

inline fun View.drawable(@DrawableRes drawableRes: Int) = context.drawable(drawableRes)
