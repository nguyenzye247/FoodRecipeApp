package com.nguyenhl.bk.foodrecipe.core.extension

import java.text.DecimalFormat

fun Float.roundFloatToTwoDecimalPlaces(): Float {
    val decimalFormat = DecimalFormat("#.##")
    return decimalFormat.format(this).toFloat()
}
