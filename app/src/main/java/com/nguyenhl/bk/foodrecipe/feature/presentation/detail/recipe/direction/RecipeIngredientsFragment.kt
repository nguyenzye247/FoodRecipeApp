package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.direction

import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.views.enforceSingleScrollDirection
import com.nguyenhl.bk.foodrecipe.databinding.FragmentRecipeDirectionIngredientBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseFragment
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDetailDto
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.RecipeDetailViewModel

class RecipeIngredientsFragment
    : BaseFragment<FragmentRecipeDirectionIngredientBinding, RecipeDetailViewModel>() {
    private lateinit var recipeDirectionDetailsAdapter: RecipeDirectionDetailsAdapter

    override fun getLazyBinding() =
        lazy { FragmentRecipeDirectionIngredientBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = activityViewModels<RecipeDetailViewModel>()

    override fun initViews() {

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
            recipeDetail.ingredientDetails
        )

        binding.apply {
            tvServes.apply {
                val serveText = "Serves for ${recipeDetail.serveCount} peoples"
                text = serveText
            }
            rvIngredientDetails.apply {
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
        fun newInstance(): RecipeIngredientsFragment = RecipeIngredientsFragment()
    }
}
