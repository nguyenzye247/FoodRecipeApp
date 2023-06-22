package com.nguyenhl.bk.foodrecipe.feature.presentation.search.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.fragment.SearchRecipeFragment
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.fragment.SearchSuggestFragment

class SearchPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = ITEM_COUNT

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            SEARCH_RECIPE_INDEX -> {
                SearchRecipeFragment.newInstance()
            }
            SEARCH_SUGGEST_INDEX -> {
                SearchSuggestFragment.newInstance()
            }
            else -> {
                SearchRecipeFragment.newInstance()
            }
        }
    }

    companion object {
        const val ITEM_COUNT = 2
        const val SEARCH_RECIPE_INDEX = 0
        const val SEARCH_SUGGEST_INDEX = 1
    }
}
