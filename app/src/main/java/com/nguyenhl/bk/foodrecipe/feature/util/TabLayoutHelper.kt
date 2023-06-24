package com.nguyenhl.bk.foodrecipe.feature.util

import androidx.viewpager2.widget.ViewPager2
import com.flyco.tablayout.listener.OnTabSelectListener
import com.nguyenhl.bk.foodrecipe.feature.widget.CommonTabLayout2

internal fun CommonTabLayout2.bindViewPager(viewPager2: ViewPager2) {
    val vp2PageChangeCallback = object: ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            this@bindViewPager.currentTab = position
        }
    }

    this.setOnTabSelectListener(object: OnTabSelectListener {
        override fun onTabSelect(position: Int) {
            viewPager2.unregisterOnPageChangeCallback(vp2PageChangeCallback)
            viewPager2.currentItem = position
            viewPager2.registerOnPageChangeCallback(vp2PageChangeCallback)
        }

        override fun onTabReselect(position: Int) {

        }

    })
    viewPager2.registerOnPageChangeCallback(vp2PageChangeCallback)
}
