package com.nguyenhl.bk.foodrecipe.feature.presentation.main.profile.fragment

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.nguyenhl.bk.foodrecipe.core.extension.launchRepeatOnResume
import com.nguyenhl.bk.foodrecipe.core.extension.launchRepeatOnStarted
import com.nguyenhl.bk.foodrecipe.core.extension.views.setVisible
import com.nguyenhl.bk.foodrecipe.databinding.FragmentProfileFavoriteRecipeBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseFragment
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto
import com.nguyenhl.bk.foodrecipe.feature.presentation.common.RecipeAdapter
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.RecipeDetailActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.MainViewModel
import kotlinx.coroutines.flow.collectLatest

class ProfileFavoriteRecipeFragment: BaseFragment<FragmentProfileFavoriteRecipeBinding, MainViewModel>() {
    private lateinit var recipeAdapter: RecipeAdapter

    override fun getLazyBinding() = lazy { FragmentProfileFavoriteRecipeBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = activityViewModels<MainViewModel>()

    override fun initViews() {
        showEmptyView(true)
        recipeAdapter = RecipeAdapter(
            onItemClick = { recipe ->
                goToRecipeDetail(recipe)
            },
            onFavoriteClick = { recipe ->
                viewModel.likeRecipe(recipe)
            }
        )
        binding.apply {
            rvProfileFavoriteRecipe.apply {
                adapter = recipeAdapter
                layoutManager = LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
            }
        }
    }

    override fun initListener() {
        binding.apply {
            recipeAdapter.addLoadStateListener { loadState ->
                showLoadingView(loadState.refresh is LoadState.Loading)
                showEmptyView(
                    loadState.append.endOfPaginationReached &&
                            recipeAdapter.itemCount < 1
                )
            }
        }
    }

    override fun initObservers() {
        viewModel.apply {
            lifecycleScope.launchRepeatOnStarted(viewLifecycleOwner) {
                getLikedRecipesPaging().collectLatest { pagingRecipes ->
                    pagingRecipes?.let {
                        recipeAdapter.submitData(pagingRecipes)
                    }
                }
            }
        }
    }

    private fun showLoadingView(isShow: Boolean) {
        binding.loading.apply {
            backgroundView.setVisible(false)
            progressBar.setVisible(isShow)
        }
    }

    private fun showEmptyView(isShowEmpty: Boolean) {
        binding.apply {
            empty.emptyView.setVisible(isShowEmpty)
            rvProfileFavoriteRecipe.setVisible(!isShowEmpty)
        }
    }

    private fun goToRecipeDetail(recipe: RecipeDto) {
        RecipeDetailActivity.startActivity(requireContext()) {
            putExtra(RecipeDetailActivity.KEY_RECIPE_DTO, recipe)
        }
    }

    companion object {
        fun newInstance(): ProfileFavoriteRecipeFragment = ProfileFavoriteRecipeFragment()
    }
}
