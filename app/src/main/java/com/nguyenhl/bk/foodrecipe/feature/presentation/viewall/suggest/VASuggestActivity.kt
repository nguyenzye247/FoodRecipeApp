package com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.suggest

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nguyenhl.bk.foodrecipe.R
import com.nguyenhl.bk.foodrecipe.core.common.KEY_USER_INFO
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observeDistinct
import com.nguyenhl.bk.foodrecipe.core.extension.parcelableExtra
import com.nguyenhl.bk.foodrecipe.core.extension.resources.txtString
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.core.extension.views.setVisible
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto
import com.nguyenhl.bk.foodrecipe.feature.presentation.common.RecipeAdapter
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.RecipeDetailActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.BaseViewAllActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.ViewAllContentType
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class VASuggestActivity : BaseViewAllActivity<VASuggestViewModel>() {
    private lateinit var suggestRecipeAdapter: RecipeAdapter

    override fun getLazyViewModel() = viewModel<VASuggestViewModel> {
        parametersOf(
            BaseInput.BaseViewAllInput.ViewAllSuggestInput(
                application,
                intent.parcelableExtra(KEY_USER_INFO)
            )
        )
    }

    override fun getRecyclerviewLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    override fun getContentType(): ViewAllContentType = ViewAllContentType.SUGGEST

    override fun initViews() {
        viewModel.setLoading(true)
        bindText()
        bindRecyclerView()
    }

    private fun bindText() {
        binding.apply {
            tvTitle.text = txtString(R.string.suggest_for_you)
            tvDescription.text = txtString(R.string.suggest_for_you_description)
        }
    }

    private fun bindRecyclerView() {
        suggestRecipeAdapter = RecipeAdapter { recipe ->
            goToRecipeDetail(recipe)
        }
        binding.rvContent.apply {
            adapter = suggestRecipeAdapter
            layoutManager = getRecyclerviewLayoutManager()
        }
    }

    override fun initListener() {
        binding.apply {
            btnBack.onClick {
                onBackPressed()
            }
        }
        suggestRecipeAdapter.addLoadStateListener { combinedLoadStates ->
            if (combinedLoadStates.refresh is LoadState.NotLoading) {
                viewModel.setLoading(false)
            }
        }
    }

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    override fun initObservers() {
        viewModel.apply {
            observeDistinct(liveIsLoading()) { isLoading ->
                showLoadingView(isLoading == true)
            }

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    getSuggestRecipesPaging().collect { suggestRecipesPaging ->
                        if (::suggestRecipeAdapter.isInitialized &&
                            suggestRecipesPaging != null
                        ) {
                            suggestRecipeAdapter.submitData(suggestRecipesPaging)
                        }
                    }
                }
            }
        }
    }

    private fun showEmptyView() {

    }

    private fun showLoadingView(isShow: Boolean) {
        binding.loading.apply {
            backgroundView.setVisible(false)
            progressBar.setVisible(isShow)
        }
    }

    private fun goToRecipeDetail(recipe: RecipeDto) {
        RecipeDetailActivity.startActivity(this) {
            putExtra(RecipeDetailActivity.KEY_RECIPE_DTO, recipe)
        }
    }

    companion object {
        fun startActivity(context: Context?, configIntent: Intent.() -> Unit) {
            context?.let {
                it.start<VASuggestActivity> {
                    apply(configIntent)
                }
            }
        }
    }
}
