package com.nguyenhl.bk.foodrecipe.feature.helper

import android.content.Context
import com.nguyenhl.bk.foodrecipe.core.common.EMPTY_TEXT
import com.nguyenhl.bk.foodrecipe.core.extension.getBaseConfig

object SessionManager {

    fun saveToken(pContext: Context, token: String) {
        pContext.getBaseConfig().token = token
    }

    fun fetchToken(pContext: Context) = pContext.getBaseConfig().token

    fun isTokenSaved(pContext: Context) = fetchToken(pContext).isNotEmpty()

    fun clearData(pContext: Context) {
        pContext.getBaseConfig().token = EMPTY_TEXT
    }
}
