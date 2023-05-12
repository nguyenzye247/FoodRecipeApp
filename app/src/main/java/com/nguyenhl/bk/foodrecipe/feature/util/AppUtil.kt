package com.nguyenhl.bk.foodrecipe.feature.util

import android.os.Build

object AppUtil {

    fun isAndroid_O_AndAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O //26
    fun isAndroid_Q_AndAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q //29
    fun isAndroid_R_AndAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R //30
    fun isAndroid_S_AndAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S //31
    fun isAndroid_TIRAMISU_AndAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU //33
}
