package com.nguyenhl.bk.foodrecipe.feature.helper

import android.content.Context
import android.content.SharedPreferences
import com.nguyenhl.bk.foodrecipe.core.common.EMPTY_TEXT

const val PREFS_KEY = "Prefs"
const val KEY_TOKEN = "key_token"
const val KEY_USER_ID = "key_user_id"

open class BaseConfig(context: Context) {
    private val prefs = context.getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)

    companion object {
        fun newInstance(context: Context) = BaseConfig(context)
    }

    fun getPrefs(): SharedPreferences = prefs

    var token: String
        get() = prefs.getString(KEY_TOKEN, EMPTY_TEXT) ?: EMPTY_TEXT
        set(token) = prefs.edit().putString(KEY_TOKEN, token).apply()

    var userId: String
        get() = prefs.getString(KEY_USER_ID, EMPTY_TEXT) ?: EMPTY_TEXT
        set(userId) = prefs.edit().putString(KEY_USER_ID, userId).apply()
}
