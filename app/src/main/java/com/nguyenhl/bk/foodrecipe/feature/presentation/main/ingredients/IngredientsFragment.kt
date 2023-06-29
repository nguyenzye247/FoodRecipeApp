package com.nguyenhl.bk.foodrecipe.feature.presentation.main.ingredients

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observeDistinct
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.core.extension.views.setVisible
import com.nguyenhl.bk.foodrecipe.databinding.FragmentIngredientsBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseFragment
import com.nguyenhl.bk.foodrecipe.feature.dto.IngredientDto
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.ingredient.IngredientDetailActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.ingredient.IngredientActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.MainViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.ingredient.AlphabetKeyAdapter
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.ingredient.VAIngredientAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class IngredientsFragment : BaseFragment<FragmentIngredientsBinding, MainViewModel>() {
    private lateinit var ingredientAdapter: VAIngredientAdapter
    private lateinit var alphabetKeyAdapter: AlphabetKeyAdapter

    override fun getLazyBinding() = lazy { FragmentIngredientsBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = activityViewModels<MainViewModel>()

    override fun initViews() {
        bindRecyclerView()
        binding.apply {

        }
    }

    override fun initListener() {
        binding.apply {
            btnSearch.onClick {
                goToIngredients()
            }

            ingredientAdapter.addLoadStateListener { combinedLoadStates ->
                viewModel.setLoading(combinedLoadStates.refresh is LoadState.Loading)
            }
        }
    }

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    override fun initObservers() {
        viewModel.apply {
            observe(liveAlphabetKey()) { alphabetKey ->
                alphabetKey?.let {
                    loadIngredientByAlphabet(it)
                }
            }

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

    private fun bindRecyclerView() {
        alphabetKeyAdapter = AlphabetKeyAdapter { alphabetKey ->
            viewModel.setAlphabetKey(alphabetKey)
        }
        binding.rvAlphabet.apply {
            setVisible(true)
            adapter = alphabetKeyAdapter
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }

        ingredientAdapter = VAIngredientAdapter { ingredientDto ->
            goToIngredientDetail(ingredientDto)
        }
        binding.rvContent.apply {
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

    private fun showLoadingView(isShow: Boolean) {
        binding.loading.apply {
            backgroundView.setVisible(false)
            progressBar.setVisible(isShow)
        }
    }

    private fun goToIngredientDetail(ingredient: IngredientDto) {
        IngredientDetailActivity.startActivity(requireContext()) {
            putExtra(IngredientDetailActivity.KEY_INGREDIENT_DTO, ingredient)
        }
    }

    private fun goToIngredients() {
        IngredientActivity.startActivity(requireContext()) {

        }
    }
}
