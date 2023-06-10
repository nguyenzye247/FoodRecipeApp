package com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.ingredient

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.nguyenhl.bk.foodrecipe.R
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observeDistinct
import com.nguyenhl.bk.foodrecipe.core.extension.resources.txtString
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.threadrelated.runDelayOnMainThread
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.core.extension.views.setVisible
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.BaseViewAllActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.ViewAllContentType
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class VAIngredientActivity : BaseViewAllActivity<VAIngredientViewModel>() {
    private lateinit var viewAllIngredientAdapter: VAIngredientAdapter
    private lateinit var alphabetKeyAdapter: AlphabetKeyAdapter

    override fun getLazyViewModel() = viewModel<VAIngredientViewModel> {
        parametersOf(
            BaseInput.BaseViewAllInput.ViewAllIngredientInput(
                application
            )
        )
    }

    override fun getRecyclerviewLayoutManager(): RecyclerView.LayoutManager {
        return StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL
        )
    }

    override fun getContentType(): ViewAllContentType = ViewAllContentType.INGREDIENT

    override fun initViews() {
        viewModel.setLoading(true)
        bindText()
        bindRecyclerView()
    }

    private fun bindText() {
        binding.apply {
            tvTitle.text = txtString(R.string.ingredients)
            tvDescription.text = txtString(R.string.suggest_for_you_description)
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
                this@VAIngredientActivity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }

        viewAllIngredientAdapter = VAIngredientAdapter { ingredientDto ->

        }
        binding.rvContent.apply {
            adapter = viewAllIngredientAdapter
            layoutManager = getRecyclerviewLayoutManager()
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

    override fun initListener() {
        binding.apply {
            btnBack.onClick {
                onBackPressed()
            }
        }
        viewAllIngredientAdapter.addLoadStateListener { combinedLoadStates ->
            viewModel.setLoading(combinedLoadStates.refresh is LoadState.Loading)
        }
    }

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    override fun initObservers() {
        viewModel.apply {
            observe(liveAlphabetKey) { alphabetKey ->
                alphabetKey?.let {
                    loadIngredientByAlphabet(it)
                }
            }

            lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    getIngredientsPaging().collectLatest { ingredientsPaging ->
                        if (::viewAllIngredientAdapter.isInitialized &&
                            ingredientsPaging != null
                        ) {
                            viewAllIngredientAdapter.submitData(lifecycle, ingredientsPaging)
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

    companion object {
        fun startActivity(context: Context?, configIntent: Intent.() -> Unit) {
            context?.let {
                it.start<VAIngredientActivity> {
                    apply(configIntent)
                }
            }
        }
    }

}
