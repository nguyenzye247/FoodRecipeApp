package com.nguyenhl.bk.foodrecipe.feature.dto.enumdata

enum class WeekDay(val value: String, var isSelected: Boolean = false) {
    Monday("Mon"),
    Tuesday("Tue"),
    Wednesday("Wed"),
    Thursday("Thurs"),
    Friday("Fri"),
    Saturday("Sat"),
    Sunday("Sun"),
}
