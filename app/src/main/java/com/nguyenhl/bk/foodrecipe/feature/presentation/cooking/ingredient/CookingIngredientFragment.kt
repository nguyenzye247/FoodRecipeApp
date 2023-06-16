package com.nguyenhl.bk.foodrecipe.feature.presentation.cooking.ingredient

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.databinding.FragmentCookingIngredientBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseFragment
import com.nguyenhl.bk.foodrecipe.feature.dto.IngredientDto
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDetailDto
import com.nguyenhl.bk.foodrecipe.feature.dto.view.DirectionItem
import com.nguyenhl.bk.foodrecipe.feature.presentation.cooking.CookingItemAdapter
import com.nguyenhl.bk.foodrecipe.feature.presentation.cooking.CookingPagerAdapter
import com.nguyenhl.bk.foodrecipe.feature.presentation.cooking.CookingViewModel

class CookingIngredientFragment :
    BaseFragment<FragmentCookingIngredientBinding, CookingViewModel>() {
    private lateinit var cookingItemAdapter: CookingItemAdapter

    override fun getLazyBinding() =
        lazy { FragmentCookingIngredientBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = activityViewModels<CookingViewModel>()

    override fun initViews() {

    }

    override fun initListener() {
        binding.apply {

        }
    }

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    override fun initObservers() {
        viewModel.apply {
            observe(liveRecipeDetail()) { recipeDetail ->
                recipeDetail?.let {
                    bindRecipeDetailDataView(it)
                }
            }

            observe(liveRecipeIngredientDetail()) { recipeIngredients ->
                recipeIngredients?.let {
                    bindRecipeIngredientsDataView(recipeIngredients)
                }
            }
        }
    }

    private fun bindRecipeDetailDataView(recipeIngredients: RecipeDetailDto) {
        cookingItemAdapter = CookingItemAdapter(
            recipeIngredients.ingredientDetails.map {
                DirectionItem(it, false)
            }
        )
        binding.apply {
            rvCookingIngredient.apply {
                adapter = cookingItemAdapter
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }
        }
    }

    private fun bindRecipeIngredientsDataView(recipeIngredients: List<IngredientDto>) {

    }

    companion object {
        fun newInstance(): CookingIngredientFragment = CookingIngredientFragment()
    }
}