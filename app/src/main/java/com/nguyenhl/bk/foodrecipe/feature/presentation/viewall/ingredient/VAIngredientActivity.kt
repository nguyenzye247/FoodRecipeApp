package com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.ingredient

import android.content.Context
import android.content.Intent
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.nguyenhl.bk.foodrecipe.R
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observeDistinct
import com.nguyenhl.bk.foodrecipe.core.extension.resources.txtString
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.core.extension.views.setVisible
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.BaseViewAllActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.ViewAllContentType
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class VAIngredientActivity : BaseViewAllActivity<VAIngredientViewModel>() {
    private lateinit var viewAllIngredientAdapter: VAIngredientAdapter

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
        viewAllIngredientAdapter = VAIngredientAdapter {

        }
        binding.rvContent.apply {
            adapter = viewAllIngredientAdapter
            layoutManager = getRecyclerviewLayoutManager()
        }
    }

    override fun initListener() {
        binding.apply {
            btnBack.onClick {
                onBackPressed()
            }
        }
        viewAllIngredientAdapter.addLoadStateListener { combinedLoadStates ->
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
