package com.nguyenhl.bk.foodrecipe.feature.util

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*

object DateFormatUtil {
    const val DATE_DEFAULT_PATTERN = "MM-dd-yyyy"
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

    fun formatApiDate(inputDate: String, format: String = "dd-MM-yyyy"): String {
        // Parse the input date string
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val date = LocalDateTime.parse(inputDate, inputFormatter)

        // Format the date to the desired pattern
        val outputFormatter = DateTimeFormatter.ofPattern(format)
        return date.format(outputFormatter)
    }

    fun formatToApiDate(inputDate: String): String {
        val inputFormat = SimpleDateFormat("MM-dd-yyyy")
        val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val date: Date = inputFormat.parse(inputDate)
        return outputFormat.format(date)
    }

    fun getAgeFrom(dob: String): Int {
        // Parse the input date string
        val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val date = LocalDateTime.parse(dob, inputFormatter).toLocalDate()

        // Calculate the age based on the current date
        val currentDate = LocalDate.now()

        return Period.between(date, currentDate).years + 1
    }
}
