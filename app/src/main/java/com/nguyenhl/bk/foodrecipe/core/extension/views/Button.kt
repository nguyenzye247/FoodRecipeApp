package com.nguyenhl.bk.foodrecipe.core.extension.views

import android.view.View
import android.widget.Button

fun Button.show() {
    if (this.visibility != View.VISIBLE) {
        this.alpha = 0f
        this.visibility = View.VISIBLE
        this.animate()
            .alpha(1f)
            .setDuration(200)
            .start()
    }
}

fun Button.hide() {
    if (this.visibility == View.VISIBLE) {
        this.animate()
            .alpha(0f)
            .setDuration(200)
            .withEndAction { this.visibility = View.GONE }
            .start()
    }
}