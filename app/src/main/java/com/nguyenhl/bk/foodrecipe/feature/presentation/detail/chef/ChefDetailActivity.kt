package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.chef

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observeDistinct
import com.nguyenhl.bk.foodrecipe.core.extension.parcelableExtra
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.views.loadImage
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.core.extension.views.setVisible
import com.nguyenhl.bk.foodrecipe.databinding.ActivityChefDetailBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.dto.AuthorDto
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto
import com.nguyenhl.bk.foodrecipe.feature.presentation.common.RecipeAdapter
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.RecipeDetailActivity
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ChefDetailActivity : BaseActivity<ActivityChefDetailBinding, ChefDetailViewModel>() {
    private val chefInfo by lazy { intent.parcelableExtra<AuthorDto>(KEY_CHEF_DTO) }
    private lateinit var recipeAdapter: RecipeAdapter

    override fun getLazyBinding() = lazy { ActivityChefDetailBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModel<ChefDetailViewModel> {
        parametersOf(
            BaseInput.ChefDetailInput(
                application,
                intent.parcelableExtra(KEY_CHEF_DTO)
            )
        )
    }

    override fun initViews() {
        showLoadingView(true)
        recipeAdapter = RecipeAdapter { recipe ->
            goToRecipeDetail(recipe)
        }

        binding.apply {
            chefInfo?.let { chef ->
                ivChefAvatar.loadImage(chef.imageUrl)
                tvChefName.text = chef.name

                rvChefRecipes.apply {
                    adapter = recipeAdapter
                    layoutManager = LinearLayoutManager(
                        this@ChefDetailActivity,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                }
            }
        }
    }

    override fun initListener() {
        binding.apply {
            btnBack.onClick {
                onBackPressed()
            }

            recipeAdapter.addLoadStateListener { combinedLoadStates ->
                viewModel.setLoading(combinedLoadStates.refresh is LoadState.Loading)
            }
        }
    }

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    override fun initObservers() {
        viewModel.apply {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    getChefRecipesPaging().collectLatest { recipePaging ->
                        if (::recipeAdapter.isInitialized &&
                            recipePaging != null
                        ) {
                            recipeAdapter.submitData(recipePaging)
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
        const val KEY_CHEF_DTO = "key_chef_dto"

        fun startActivity(context: Context?, configIntent: Intent.() -> Unit) {
            context?.let {
                it.start<ChefDetailActivity> {
                    apply(configIntent)
                }
            }
        }
    }

}
