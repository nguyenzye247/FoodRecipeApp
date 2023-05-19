package com.nguyenhl.bk.foodrecipe.core.extension.views

import android.view.View
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams

fun View.setPaddingBottom(value: Int) {
    this.setPadding(
        paddingLeft, paddingTop, paddingRight, value
    )
}

fun View.setPaddingTop(value: Int) {
    this.setPadding(
        paddingLeft, value, paddingRight, paddingBottom
    )
}

fun View.setVisible(visible: Boolean, invisible: Int = View.GONE) {
    visibility = if (visible) View.VISIBLE else invisible
}

fun View.setMarginTop(value: Int) = updateLayoutParams<ViewGroup.MarginLayoutParams> {
    topMargin = value
}

fun View.setMarginBottom(value: Int) = updateLayoutParams<ViewGroup.MarginLayoutParams> {
    bottomMargin = value
}
