/*
 * Copyright 2019 Louis Cognault Ayeva Derman. Use of this source code is governed by the Apache 2.0 license.
 */

@file:Suppress("NOTHING_TO_INLINE")

package com.nguyenhl.bk.foodrecipe.core.extension

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.os.Build.VERSION.SDK_INT
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.StringRes
import com.nguyenhl.bk.foodrecipe.core.extension.resources.txt
import es.dmoral.toasty.Toasty
import timber.log.Timber

fun Context.createToast(text: CharSequence, duration: Int): Toast {
    val ctx = if (SDK_INT == 25) SafeToastCtx(this) else this
    return Toast.makeText(ctx, text, duration)
}

fun Context.createToast(@StringRes resId: Int, duration: Int): Toast {
    return createToast(txt(resId), duration)
}

inline fun Context.toast(
    @StringRes msgResId: Int
) = createToast(msgResId, Toast.LENGTH_SHORT).show()

inline fun View.toast(@StringRes msgResId: Int) = context.toast(msgResId)


inline fun Context.toast(msg: CharSequence) = createToast(msg, Toast.LENGTH_SHORT).show()

inline fun View.toast(msg: CharSequence) = context.toast(msg)


inline fun Context.longToast(
    @StringRes msgResId: Int
) = createToast(msgResId, Toast.LENGTH_LONG).show()

inline fun View.longToast(@StringRes msgResId: Int) = context.longToast(msgResId)

inline fun Context.longToast(msg: CharSequence) = createToast(msg, Toast.LENGTH_LONG).show()

inline fun View.longToast(msg: CharSequence) = context.longToast(msg)

/**
 * Avoids [WindowManager.BadTokenException] on API 25.
 */
private class SafeToastCtx(ctx: Context) : ContextWrapper(ctx) {

    private val toastWindowManager by lazy(LazyThreadSafetyMode.NONE) { ToastWindowManager(baseContext.windowManager) }
    private val toastLayoutInflater by lazy(LazyThreadSafetyMode.NONE) {
        baseContext.layoutInflater.cloneInContext(this)
    }

    override fun getApplicationContext(): Context = SafeToastCtx(baseContext.applicationContext)
    override fun getSystemService(name: String): Any? = when (name) {
        Context.LAYOUT_INFLATER_SERVICE -> toastLayoutInflater
        Context.WINDOW_SERVICE -> toastWindowManager
        else -> super.getSystemService(name)
    }

    private class ToastWindowManager(private val base: WindowManager) : WindowManager by base {

        @SuppressLint("LogNotTimber") // Timber is not a dependency here, but lint passes through.
        override fun addView(view: View?, params: ViewGroup.LayoutParams?) {
            try {
                base.addView(view, params)
            } catch (e: WindowManager.BadTokenException) {
                Timber.tag("SafeToast").e("Couldn't add Toast to WindowManager")
            }
        }
    }
}

fun Context.createToastError(text: CharSequence, duration: Int): Toast {
    return Toasty.error(this, text, duration, true)
}

fun Context.createToastError(@StringRes resId: Int, duration: Int): Toast {
    return createToastError(txt(resId), duration)
}

fun Context.toastError(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    createToastError(text, duration).show()
}

fun Context.toastError(@StringRes resId: Int, duration: Int) {
    createToastError(resId, duration).show()
}

fun Context.createToastSuccess(text: CharSequence, duration: Int): Toast {
    return Toasty.success(this, text, duration, true)
}

fun Context.createToastSuccess(@StringRes resId: Int, duration: Int): Toast {
    return createToastSuccess(txt(resId), duration)
}

fun Context.toastSuccess(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    createToastSuccess(text, duration).show()
}

fun Context.toastSuccess(@StringRes resId: Int, duration: Int) {
    createToastSuccess(resId, duration).show()
}
