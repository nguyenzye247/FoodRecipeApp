package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.direction

import androidx.fragment.app.activityViewModels
import com.nguyenhl.bk.foodrecipe.databinding.FragmentRecipeDirectionIngredientBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseFragment
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.RecipeDetailViewModel

class RecipeIngredientsFragment
    : BaseFragment<FragmentRecipeDirectionIngredientBinding, RecipeDetailViewModel>() {
    private val serveCount by lazy {  }
    private val ingredientDirections by lazy {  }

    override fun getLazyBinding() = lazy { FragmentRecipeDirectionIngredientBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = activityViewModels<RecipeDetailViewModel>()

    override fun initViews() {


        binding.apply {
            val servesText = "Serves for $serveCount peoples"
            tvServes.text = servesText
        }
    }

    override fun initListener() {

    }

    override fun initObservers() {

    }

    companion object {
        fun newInstance(): RecipeIngredientsFragment = RecipeIngredientsFragment()
    }
}
