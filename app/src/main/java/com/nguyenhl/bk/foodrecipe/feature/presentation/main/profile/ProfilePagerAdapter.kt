package com.nguyenhl.bk.foodrecipe.feature.presentation.main.profile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.nguyenhl.bk.foodrecipe.R
import com.nguyenhl.bk.foodrecipe.core.extension.resources.txtString
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.profile.fragment.ProfileFavoriteRecipeFragment
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.profile.fragment.ProfileHealthStatusFragment
import com.nguyenhl.bk.foodrecipe.feature.widget.SlidingAdapter

class ProfilePagerAdapter(
    private val fragmentActivity: FragmentActivity
): SlidingAdapter(fragmentActivity) {
    override fun getItemCount(): Int = ITEM_COUNT

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            HEALTH_PAGE_INDEX -> {
                ProfileHealthStatusFragment.newInstance()
            }
            LIKE_PAGE_INDEX -> {
                ProfileFavoriteRecipeFragment.newInstance()
            }
            else -> {
                ProfileHealthStatusFragment.newInstance()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when(position) {
            HEALTH_PAGE_INDEX -> {
                fragmentActivity.txtString(R.string.health_status)
            }
            LIKE_PAGE_INDEX -> {
                fragmentActivity.txtString(R.string.favorite)
            }
            else -> {
                fragmentActivity.txtString(R.string.health_status)
            }
        }
    }

    companion object {
        const val ITEM_COUNT = 2
        const val HEALTH_PAGE_INDEX = 0
        const val LIKE_PAGE_INDEX = 1
    }
}
