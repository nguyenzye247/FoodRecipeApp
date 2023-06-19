package com.nguyenhl.bk.foodrecipe.feature.presentation.search.recipe

import android.content.Context
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.nguyenhl.bk.foodrecipe.core.extension.launchRepeatOnStarted
import com.nguyenhl.bk.foodrecipe.core.extension.views.enforceSingleScrollDirection
import com.nguyenhl.bk.foodrecipe.core.extension.views.setVisible
import com.nguyenhl.bk.foodrecipe.databinding.FragmentSearchRecipeBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseFragment
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.direction.RecipeMethodsFragment
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.SearchInteractionListener
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.SearchViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.search.adapter.SearchRecipePagingAdapter
import kotlinx.coroutines.flow.collectLatest

class SearchRecipeFragment : BaseFragment<FragmentSearchRecipeBinding, SearchViewModel>() {
    private lateinit var searchRecipePagingAdapter: SearchRecipePagingAdapter
    private lateinit var searchInteractionListener: SearchInteractionListener

    override fun getLazyBinding() =
        lazy { FragmentSearchRecipeBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = activityViewModels<SearchViewModel>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SearchInteractionListener) {
            searchInteractionListener = context
        }
    }

    override fun initViews() {
        searchRecipePagingAdapter = SearchRecipePagingAdapter(
            onItemClick = { recipe ->
                searchInteractionListener.onSelectRecipe(recipe)
            },
            onFavoriteClick = { recipe ->
                searchInteractionListener.onFavoriteRecipe(recipe)
            }
        )

        binding.apply {
            rvSearchRecipe.apply {
                adapter = searchRecipePagingAdapter
                layoutManager = LinearLayoutManager(
                    context,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                enforceSingleScrollDirection()
            }
        }
    }

    override fun initListener() {
        searchRecipePagingAdapter.addLoadStateListener {
            showLoadingView(it.refresh is LoadState.Loading)
        }
    }

    override fun initObservers() {
        viewModel.apply {
            lifecycleScope.launchRepeatOnStarted(viewLifecycleOwner) {
                getRandomRecipePaging().collectLatest { recipePaging ->
                    recipePaging?.let {
                        searchRecipePagingAdapter.submitData(it)
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

    companion object {
        fun newInstance(): SearchRecipeFragment = SearchRecipeFragment()
    }
}
