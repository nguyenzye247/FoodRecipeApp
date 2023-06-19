package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.direction

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.views.enforceSingleScrollDirection
import com.nguyenhl.bk.foodrecipe.databinding.FragmentRecipeDirectionMethodBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseFragment
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDetailDto
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

    private fun bindRecipeDetailDataView(recipeDetail: RecipeDetailDto) {
        recipeDirectionDetailsAdapter = RecipeDirectionDetailsAdapter(
            recipeDetail.methods
        )

        binding.apply {
            rvMethodDetails.apply {
                adapter = recipeDirectionDetailsAdapter
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                enforceSingleScrollDirection()
            }
        }
    }

    companion object {
        fun newInstance(): RecipeMethodsFragment = RecipeMethodsFragment()
    }
}