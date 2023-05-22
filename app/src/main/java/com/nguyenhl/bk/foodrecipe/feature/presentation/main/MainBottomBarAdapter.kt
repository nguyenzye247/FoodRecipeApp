package com.nguyenhl.bk.foodrecipe.feature.presentation.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.calendar.CalendarFragment
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.HomeFragment
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.ingredients.IngredientsFragment
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.profile.ProfileFragment
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.search.SearchFragment

class MainBottomBarAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    companion object {
        private const val MAIN_MENU_ITEM_COUNT = 5
        const val HOME_POS = 0
        const val INGREDIENTS_POS = 1
        const val SEARCH_POS = 2
        const val CALENDAR_POS = 3
        const val PROFILE_POS = 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            HOME_POS -> HomeFragment()
            INGREDIENTS_POS -> IngredientsFragment()
            SEARCH_POS -> SearchFragment()
            CALENDAR_POS -> CalendarFragment()
            PROFILE_POS -> ProfileFragment()
            else -> {
                HomeFragment()
            }
        }
    }

    override fun getItemCount() = MAIN_MENU_ITEM_COUNT
}
