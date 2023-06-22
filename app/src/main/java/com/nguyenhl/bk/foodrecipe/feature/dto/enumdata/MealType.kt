package com.nguyenhl.bk.foodrecipe.feature.dto.enumdata

enum class MealType(val value: String) {
    BREAKFAST("breakfast"),
    LUNCH("lunch"),
    DINNER("dinner");

    companion object {
        fun getMealTypeFrom(mealType: String): MealType? {
            return when (mealType) {
                BREAKFAST.value -> BREAKFAST
                LUNCH.value -> LUNCH
                DINNER.value -> DINNER
                else -> null
            }
        }
    }
}
