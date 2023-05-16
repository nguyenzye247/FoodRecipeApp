package com.nguyenhl.bk.foodrecipe.core.extension.views

import android.view.View

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
