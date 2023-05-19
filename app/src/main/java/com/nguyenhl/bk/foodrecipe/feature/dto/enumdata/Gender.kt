package com.nguyenhl.bk.foodrecipe.feature.dto.enumdata

enum class Gender(val value: Int) {
    MALE(0),
    FEMALE(1),
    OTHER(2);

    companion object {
        fun getGenderFrom(value: Int): Gender? {
            return when (value) {
                MALE.value -> MALE
                FEMALE.value -> FEMALE
                OTHER.value -> OTHER
                else -> null
            }
        }
    }
}
