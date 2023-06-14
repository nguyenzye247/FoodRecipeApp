package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.direction.RecipeIngredientsFragment
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.direction.RecipeMethodsFragment

class RecipeDirectionPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    companion object {
        const val ITEM_COUNT = 2
        const val INGREDIENT_PAGE_INDEX = 0
        const val METHOD_PAGE_INDEX = 1
    }

    override fun getItemCount(): Int = ITEM_COUNT

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            INGREDIENT_PAGE_INDEX -> {
                RecipeIngredientsFragment.newInstance()
            }
            METHOD_PAGE_INDEX -> {
                RecipeMethodsFragment.newInstance()
            }
            else -> {
                RecipeIngredientsFragment.newInstance()
            }
        }
    }
}
