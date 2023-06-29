package com.nguyenhl.bk.foodrecipe.feature.dto.detect

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetectResultDto(
    val xStart: Float,
    val yStart: Float,
    val width: Float,
    val height: Float,
    val confidence: Float,
    val classResultName: String
) : Parcelable
