package com.nguyenhl.bk.foodrecipe.feature.presentation.ingredient

import android.content.Context
import android.content.Intent
import android.view.inputmethod.EditorInfo
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observeDistinct
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.views.hideKeyboard
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.core.extension.views.setVisible
import com.nguyenhl.bk.foodrecipe.databinding.ActivityIngredientsBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.dto.IngredientDto
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.ingredient.IngredientDetailActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.ingredient.VAIngredientAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class IngredientActivity : BaseActivity<ActivityIngredientsBinding, IngredientViewModel>() {
    private lateinit var ingredientAdapter: VAIngredientAdapter

    override fun getLazyBinding() = lazy { ActivityIngredientsBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModel<IngredientViewModel> {
        parametersOf(
            BaseInput.IngredientInput(
                application
            )
        )
    }

    override fun initViews() {
        binding.apply {
            ingredientAdapter = VAIngredientAdapter { ingredientDto ->
                goToIngredientDetail(ingredientDto)
            }
            binding.rvIngredients.apply {
                adapter = ingredientAdapter
                layoutManager = StaggeredGridLayoutManager(
                    2,
                    StaggeredGridLayoutManager.VERTICAL
                )
                itemAnimator = object : DefaultItemAnimator() {
                    override fun animateChange(
                        oldHolder: RecyclerView.ViewHolder,
                        newHolder: RecyclerView.ViewHolder,
                        preInfo: ItemHolderInfo,
                        postInfo: ItemHolderInfo
                    ): Boolean {
                        return false
                    }
                }
            }
        }
    }

    override fun initListener() {
        binding.apply {
            etSearch.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_GO ||
                    actionId == EditorInfo.IME_ACTION_NEXT ||
                    actionId == EditorInfo.IME_ACTION_DONE) {
                    searchIngredient()
                }
                true
            }
            btnBack.onClick {
                onBackPressed()
            }
            ingredientAdapter.addLoadStateListener { loadState ->
                viewModel.setLoading(loadState.refresh is LoadState.Loading)
                showEmptyView(
                    loadState.append.endOfPaginationReached &&
                            ingredientAdapter.itemCount < 1
                )
            }
            tipSearch.setEndIconOnClickListener {
                searchIngredient()
            }
        }
    }

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    override fun initObservers() {
        viewModel.apply {
            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    getIngredientsPaging().collectLatest { ingredientsPaging ->
                        if (::ingredientAdapter.isInitialized &&
                            ingredientsPaging != null
                        ) {
                            ingredientAdapter.submitData(lifecycle, ingredientsPaging)
                        }
                    }
                }
            }

            observeDistinct(liveIsLoading()) { isLoading ->
                showLoadingView(isLoading == true)
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
            rvIngredients.setVisible(!isShowEmpty)
        }
    }

    private fun searchIngredient() {
        binding.etSearch.apply {
            hideKeyboard()
            val searchText = text.toString()

            viewModel.searchIngredient(searchText)
        }
    }

    private fun goToIngredientDetail(ingredient: IngredientDto) {
        IngredientDetailActivity.startActivity(this) {
            putExtra(IngredientDetailActivity.KEY_INGREDIENT_DTO, ingredient)
        }
    }

    companion object {
        fun startActivity(context: Context?, configIntent: Intent.() -> Unit) {
            context?.let {
                it.start<IngredientActivity> {
                    apply(configIntent)
                }
            }
        }
    }
}
