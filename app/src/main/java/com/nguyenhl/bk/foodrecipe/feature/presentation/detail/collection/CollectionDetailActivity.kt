package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.collection

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
import com.nguyenhl.bk.foodrecipe.databinding.ActivityCollectionDetailBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.dto.CollectionDto
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto
import com.nguyenhl.bk.foodrecipe.feature.presentation.common.RecipeAdapter
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.RecipeDetailActivity
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CollectionDetailActivity :
    BaseActivity<ActivityCollectionDetailBinding, CollectionDetailViewModel>() {
    private val collectionInfo by lazy { intent.parcelableExtra<CollectionDto>(KEY_COLLECTION_DTO) }
    private lateinit var recipeAdapter: RecipeAdapter

    override fun getLazyBinding() = lazy { ActivityCollectionDetailBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModel<CollectionDetailViewModel> {
        parametersOf(
            BaseInput.CollectionDetailInput(
                application,
                intent.parcelableExtra(KEY_COLLECTION_DTO)
            )
        )
    }

    override fun initViews() {
        adjustScreenSize(binding.btnBack)
        showLoadingView(true)
        recipeAdapter = RecipeAdapter { recipe ->
            goToRecipeDetail(recipe)
        }

        binding.apply {
            collectionInfo?.let { collection ->
                ivCollectionThumbnail.loadImage(collection.imageUrl)
                tvCollectionTitle.text = collection.name
                contentLayout.apply {
                    tvCollectionDescription.text = collection.description
                    rvCollectionRecipes.apply {
                        adapter = recipeAdapter
                        layoutManager = LinearLayoutManager(
                            this@CollectionDetailActivity,
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                    }
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
                    getCollectionRecipesPaging().collect { recipePaging ->
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
        const val KEY_COLLECTION_DTO = "key_collection_dto"

        fun startActivity(context: Context?, configIntent: Intent.() -> Unit) {
            context?.let {
                it.start<CollectionDetailActivity> {
                    apply(configIntent)
                }
            }
        }
    }
}
