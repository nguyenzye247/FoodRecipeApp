package com.nguyenhl.bk.foodrecipe.core.extension

import android.content.Context
import com.nguyenhl.bk.foodrecipe.feature.helper.BaseConfig

fun Context.getBaseConfig() : BaseConfig {
    return BaseConfig.newInstance(this)
}
