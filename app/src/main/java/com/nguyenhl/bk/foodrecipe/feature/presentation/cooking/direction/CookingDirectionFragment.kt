package com.nguyenhl.bk.foodrecipe.feature.presentation.cooking.direction

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.databinding.FragmentCookingDirectionBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseFragment
import com.nguyenhl.bk.foodrecipe.feature.dto.IngredientDto
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDetailDto
import com.nguyenhl.bk.foodrecipe.feature.dto.view.DirectionItem
import com.nguyenhl.bk.foodrecipe.feature.presentation.cooking.CookingButtonType
import com.nguyenhl.bk.foodrecipe.feature.presentation.cooking.CookingItemAdapter
import com.nguyenhl.bk.foodrecipe.feature.presentation.cooking.CookingPagerAdapter
import com.nguyenhl.bk.foodrecipe.feature.presentation.cooking.CookingViewModel

class CookingDirectionFragment : BaseFragment<FragmentCookingDirectionBinding, CookingViewModel>() {
    private lateinit var cookingItemAdapter: CookingItemAdapter

    override fun getLazyBinding() =
        lazy { FragmentCookingDirectionBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = activityViewModels<CookingViewModel>()

    override fun initViews() {

    }

    override fun initListener() {
        binding.apply {
            btnFinishCooking.onClick {
                viewModel.setCookingButtonType(CookingButtonType.FINISH)
            }
            btnPreviousCooking.onClick {
                viewModel.setCookingButtonType(CookingButtonType.PREVIOUS)
            }
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
        }
    }

    private fun bindRecipeDetailDataView(recipeIngredients: RecipeDetailDto) {
        cookingItemAdapter = CookingItemAdapter(
            recipeIngredients.methods.map {
                DirectionItem(it, false)
            }
        )
        binding.apply {
            rvCookingDirection.apply {
                adapter = cookingItemAdapter
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }
        }
    }


    companion object {
        fun newInstance(): CookingDirectionFragment = CookingDirectionFragment()
    }
}
