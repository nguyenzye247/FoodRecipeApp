package com.nguyenhl.bk.foodrecipe.feature.helper

import android.content.Context
import android.content.SharedPreferences
import com.nguyenhl.bk.foodrecipe.core.common.PREFS_KEY

open class BaseConfig(context: Context) {
    private val prefs = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)

    companion object {
        fun newInstance(context: Context) = BaseConfig(context)
    }

    fun getPrefs(): SharedPreferences = prefs
}
