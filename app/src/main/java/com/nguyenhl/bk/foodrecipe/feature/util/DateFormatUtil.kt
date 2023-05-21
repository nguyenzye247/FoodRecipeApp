package com.nguyenhl.bk.foodrecipe.feature.util

import java.text.SimpleDateFormat
import java.util.*

object DateFormatUtil {
    private const val DEFAULT_PATTERN = "MM/dd/yyyy"

    fun formatSimpleDate(date: Date, pattern: String = DEFAULT_PATTERN): String {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.format(date)
    }
}
