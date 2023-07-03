package com.nguyenhl.bk.foodrecipe.feature.dto.weeklyplan

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeeklyPlanDto(
    val idApi: String,
    val idWeeklyPlan: String,
    val idWeeklyPlanDetail: String,
    val name: String,
    val imageUrl: String
) : Parcelable
