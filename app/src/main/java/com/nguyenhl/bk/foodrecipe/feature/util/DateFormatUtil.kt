package com.nguyenhl.bk.foodrecipe.feature.util

import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object DateFormatUtil {
    private const val DATE_DEFAULT_PATTERN = "MM-dd-yyyy"
    private const val DATE_VALID_PATTERN = "dd/MM/yyyy"

    private const val DATE_RECIPE_PATTERN = "yyyy-MM-dd"

    fun formatSimpleDate(date: Date, pattern: String = DATE_DEFAULT_PATTERN): String {
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.format(date)
    }

    fun changeDateFormat(dateString: String): String {
        return try {
            val inputFormat = DateTimeFormatter.ofPattern(DATE_DEFAULT_PATTERN)
            val outputFormat = DateTimeFormatter.ofPattern(DATE_VALID_PATTERN)
            val date: LocalDate? = LocalDate.parse(dateString, inputFormat)
            if (date == null) dateString else outputFormat.format(date)
        } catch (ex: Exception) {
            dateString
        }
    }

    fun formatDateForRecipeSearch(date: LocalDate): String {
        return date.format(DateTimeFormatter.ofPattern(DATE_RECIPE_PATTERN))
    }
}
