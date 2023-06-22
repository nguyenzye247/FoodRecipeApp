package com.nguyenhl.bk.foodrecipe.feature.presentation.search.fragment

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
            viewModel.isMealTypeSearch,
            onItemClick = { recipe ->
                searchInteractionListener.onSelectRecipe(recipe)
            },
            onFavoriteClick = { recipe ->
                searchInteractionListener.onFavoriteRecipe(recipe)
            },
            onAddToClick = { recipe ->
                searchInteractionListener.onRecipeAddTo(recipe)
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
        searchRecipePagingAdapter.addLoadStateListener { loadState ->
            showLoadingView(loadState.refresh is LoadState.Loading)
            showEmptyView(
                loadState.append.endOfPaginationReached &&
                        searchRecipePagingAdapter.itemCount < 1
            )
        }
    }

    override fun initObservers() {
        viewModel.apply {
            lifecycleScope.launchRepeatOnStarted(viewLifecycleOwner) {
                getSearchRecipePaging().collectLatest { recipePaging ->
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

    private fun showEmptyView(isShowEmpty: Boolean) {
        binding.apply {
            empty.emptyView.setVisible(isShowEmpty)
            rvSearchRecipe.setVisible(!isShowEmpty)
        }
    }

    companion object {
        fun newInstance(): SearchRecipeFragment = SearchRecipeFragment()
    }
}
