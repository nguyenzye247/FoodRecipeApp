package com.nguyenhl.bk.foodrecipe.feature.dto.enumdata

enum class Gender(val value: Int, val title: String) {
    FEMALE(0, "Female"),
    MALE(1, "Male"),
    OTHER(2, "Other");

    companion object {
        fun getGenderFrom(value: Int): Gender? {
            return when (value) {
                MALE.value -> MALE
                FEMALE.value -> FEMALE
                OTHER.value -> OTHER
                else -> null
            }
        }

        fun getGenderFrom(title: String): Gender? {
            return when (title) {
                MALE.title -> MALE
                FEMALE.title -> FEMALE
                OTHER.title -> OTHER
                else -> null
            }
        }
    }
}
