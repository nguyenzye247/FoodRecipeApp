package com.nguyenhl.bk.foodrecipe.feature.presentation.cooking

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.nguyenhl.bk.foodrecipe.feature.presentation.cooking.direction.CookingDirectionFragment
import com.nguyenhl.bk.foodrecipe.feature.presentation.cooking.ingredient.CookingIngredientFragment

class CookingPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {

    companion object {
        const val ITEM_COUNT = 2
        const val INGREDIENT_PAGE_INDEX = 0
        const val DIRECTION_PAGE_INDEX = 1
    }

    override fun getItemCount(): Int = ITEM_COUNT

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            INGREDIENT_PAGE_INDEX -> {
                CookingIngredientFragment.newInstance()
            }

            DIRECTION_PAGE_INDEX -> {
                CookingDirectionFragment.newInstance()
            }

            else -> {
                CookingIngredientFragment.newInstance()
            }
        }
    }

}
