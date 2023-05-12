package com.nguyenhl.bk.foodrecipe.core.extension

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager

inline val Context.windowManager get() =
    getSystemService(Context.WINDOW_SERVICE) as WindowManager

inline val Context.layoutInflater: LayoutInflater get() =
    getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

inline val View.layoutInflater: LayoutInflater get() = context.layoutInflater
