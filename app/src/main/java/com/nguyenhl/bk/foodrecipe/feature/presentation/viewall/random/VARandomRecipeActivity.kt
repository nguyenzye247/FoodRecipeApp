package com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.random

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

class VARandomRecipeActivity : BaseViewAllActivity<VARandomRecipeViewModel>() {
    private lateinit var randomRecipeAdapter: RecipeAdapter

    override fun getLazyViewModel() = viewModel<VARandomRecipeViewModel> {
        parametersOf(
            BaseInput.BaseViewAllInput.ViewAllRandomInput(
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

    override fun getContentType(): ViewAllContentType = ViewAllContentType.RANDOM

    override fun initViews() {
        viewModel.setLoading(true)
        bindText()
        bindRecyclerView()
    }

    private fun bindText() {
        binding.apply {
            tvTitle.text = txtString(R.string.daily_inspirations)
            tvDescription.text = txtString(R.string.suggest_for_you_description)
        }
    }

    private fun bindRecyclerView() {
        randomRecipeAdapter = RecipeAdapter(
            onItemClick = { recipe ->
                goToRecipeDetail(recipe)
            },
            onFavoriteClick = { recipe ->
                viewModel.likeRecipe(recipe)
            }
        )
        binding.rvContent.apply {
            adapter = randomRecipeAdapter
            layoutManager = getRecyclerviewLayoutManager()
        }
    }

    override fun initListener() {
        binding.apply {
            btnBack.onClick {
                onBackPressed()
            }
        }
        randomRecipeAdapter.addLoadStateListener { combinedLoadStates ->
            if (combinedLoadStates.refresh is LoadState.NotLoading) {
                viewModel.setLoading(false)
            }
        }
    }

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    override fun initObservers() {
        viewModel.apply {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    getRandomRecipesPaging().collect { randomRecipesPaging ->
                        if (::randomRecipeAdapter.isInitialized &&
                            randomRecipesPaging != null
                        ) {
                            randomRecipeAdapter.submitData(randomRecipesPaging)
                        }
                    }
                }
            }

            observeDistinct(liveIsLoading()) { isLoading ->
                showLoadingView(isLoading == true)
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
                it.start<VARandomRecipeActivity> {
                    apply(configIntent)
                }
            }
        }
    }
}
