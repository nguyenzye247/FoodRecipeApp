package com.nguyenhl.bk.foodrecipe.feature.util

import android.os.Build
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.profile.bmi.BmiLevel
import java.lang.Math.round
import kotlin.math.roundToInt

object AppUtil {

    fun isAndroid_N_AndAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N //26
    fun isAndroid_O_AndAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O //26
    fun isAndroid_Q_AndAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q //29
    fun isAndroid_R_AndAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.R //30
    fun isAndroid_S_AndAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S //31
    fun isAndroid_TIRAMISU_AndAbove() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU //33

    fun calculateBMI(age: Int, height: Float, weight: Float): Float {
        // Convert height from cm to meters
        val heightInMeters = height / 100

        // Calculate BMI
        val bmi = weight / (heightInMeters * heightInMeters)

        // Round the BMI value to one decimal place
        return (bmi * 10).roundToInt() / 10.toFloat()
    }

    fun calculateBMR(weight: Float, height: Float, age: Int, isMale: Boolean): Double {
        val bmr: Double = if (isMale) {
            // BMR calculation for males
            66 + (13.7 * weight) + (5 * height) - (6.8 * age)
        } else {
            // BMR calculation for females
            655 + (9.6 * weight) + (1.8 * height) - (4.7 * age)
        }
        return bmr
    }

    fun getBmiLevel(bmi: Float): BmiLevel {
        return when {
            bmi < 18.5f -> BmiLevel.UNDERWEIGHT
            bmi >= 18.5f && bmi < 25f -> BmiLevel.NORMAL
            else -> BmiLevel.OVERWEIGHT
        }
    }
}
