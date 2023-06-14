package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.direction

import androidx.fragment.app.activityViewModels
import com.nguyenhl.bk.foodrecipe.databinding.FragmentRecipeDirectionMethodBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseFragment
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.RecipeDetailViewModel

class RecipeMethodsFragment :
    BaseFragment<FragmentRecipeDirectionMethodBinding, RecipeDetailViewModel>() {
    private lateinit var recipeDirectionDetailsAdapter: RecipeDirectionDetailsAdapter

    override fun getLazyBinding() =
        lazy { FragmentRecipeDirectionMethodBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = activityViewModels<RecipeDetailViewModel>()

    override fun initViews() {
        binding.apply {

        }
    }

    override fun initListener() {

    }

    override fun initObservers() {

    }

    companion object {
        fun newInstance(): RecipeMethodsFragment = RecipeMethodsFragment()
    }
}