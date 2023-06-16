package com.nguyenhl.bk.foodrecipe.feature.dto.view

import androidx.annotation.DrawableRes
import com.flyco.tablayout.listener.CustomTabEntity

class TabEntity(
    val title: String,
    @DrawableRes val iconSelected: Int,
    @DrawableRes val iconUnselected: Int
): CustomTabEntity {
    override fun getTabTitle(): String = title

    override fun getTabSelectedIcon(): Int = iconSelected

    override fun getTabUnselectedIcon(): Int = iconUnselected
}
