package com.nguyenhl.bk.foodrecipe.feature.widget

import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.flyco.tablayout.CommonTabLayout
import com.flyco.tablayout.listener.OnTabSelectListener
import java.util.Random

fun CommonTabLayout.bindViewPager(
    viewPager2: ViewPager2,
    pageChangeCallback: OnPageChangeCallback
) {
    val mRandom = Random()
    this.setOnTabSelectListener(object: OnTabSelectListener {
        override fun onTabSelect(position: Int) {
            viewPager2.currentItem = position
        }

        override fun onTabReselect(position: Int) {
            if (position == 0) {
                this@bindViewPager.showMsg(0, mRandom.nextInt(100) + 1)
            }
        }

    })

    viewPager2.registerOnPageChangeCallback(pageChangeCallback)
}
