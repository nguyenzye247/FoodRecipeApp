package com.nguyenhl.bk.foodrecipe.feature.util

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout

class ScrollAwareBehavior(context: Context, attrs: AttributeSet) :
    AppBarLayout.ScrollingViewBehavior(context, attrs) {

    private var initialY = 0f

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        if (initialY == 0f) {
            initialY = dependency.y
        }

        val newY = initialY + dependency.y
        child.translationY = newY

        return true
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        return axes == View.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        if (dyConsumed > 0 && child.visibility == View.VISIBLE) {
            child.visibility = View.INVISIBLE
        } else if (dyConsumed == 0 && child.visibility == View.INVISIBLE) {
            child.visibility = View.VISIBLE
        }
    }
}
