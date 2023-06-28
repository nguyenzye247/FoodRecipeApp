package com.nguyenhl.bk.foodrecipe.feature.util

import androidx.core.text.isDigitsOnly
import com.nguyenhl.bk.foodrecipe.feature.util.DateFormatUtil.changeDateFormat
import com.wajahatkarim3.easyvalidation.core.view_ktx.minLength
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail

internal fun String.validDate(onInvalid: () -> Unit) {
    val regex =
        "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})\$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))\$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})\$"
    if (matches(regex.toRegex())) {
        return
    }
    onInvalid()
}

internal fun String.checkNumber(onInvalid: (message: String) -> Unit) {
    var isValid = true
    var message = ""
    nonEmpty {
        // error
        isValid = false
        message = "Field can not be empty"
    }
    if (!isNumeric(this)) {
        // error
        isValid = false
        message = "Field can only be number"
    }
    if (!isValid) {
        onInvalid(message)
    }
}

internal fun String.checkEmpty(onInvalid: (message: String) -> Unit) {
    nonEmpty {
        // error
        onInvalid("Field can not be empty")
    }
}

internal fun String.checkDate(onInvalid: (message: String) -> Unit) {
    val dateString = changeDateFormat(this)
    var isValid = true
    var message = ""
    dateString.nonEmpty {
        // error
        isValid = false
        message = "Field can not be empty"
    }
    dateString.validDate {
        // error
        isValid = false
        message = "Invalid date format"
    }
    if (!isValid) {
        onInvalid(message)
    }
}

internal fun String.checkEmail(onInvalid: (message: String) -> Unit) {
    var isValid = true
    var message = ""
    nonEmpty {
        // error
        isValid = false
        message = "Field can not be empty"
    }
    validEmail {
        // error
        isValid = false
        message = "Wrong email format"
    }
    if (!isValid) {
        onInvalid(message)
    }
}

internal fun String.checkPassword(onInvalid: (str: String) -> Unit) {
    var isValid = true
    var message = ""
    nonEmpty {
        // error
        isValid = false
        message = "Field can not be empty"
    }
    minLength(8) {
        // error
        isValid = false
        message = "Password length must be greater than 8"
    }
    if (!isValid) {
        onInvalid(message)
        return
    }
}

fun isNumeric(text: String): Boolean {
    return try {
        text.toInt()
        true
    } catch (e: NumberFormatException) {
        try {
            text.toFloat()
            true
        } catch (e: NumberFormatException) {
            try {
                text.toDouble()
                true
            } catch (e: NumberFormatException) {
                false
            }
        }
    }
}
