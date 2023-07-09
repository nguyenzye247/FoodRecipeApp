package com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.chef

import android.content.Context
import android.content.Intent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
import com.nguyenhl.bk.foodrecipe.feature.dto.AuthorDto
import com.nguyenhl.bk.foodrecipe.feature.presentation.detail.chef.ChefDetailActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.BaseViewAllActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.ViewAllContentType
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class VAChefActivity : BaseViewAllActivity<VAChefViewModel>() {
    private lateinit var viewAllChefAdapter: VAChefAdapter

    override fun getLazyViewModel() = viewModel<VAChefViewModel> {
        parametersOf(BaseInput.BaseViewAllInput.ViewAllTopChefInput(application))
    }

    override fun getRecyclerviewLayoutManager(): RecyclerView.LayoutManager {
        return StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL
        )
    }

    override fun getContentType(): ViewAllContentType = ViewAllContentType.TOP_CHEF

    override fun initViews() {
        viewModel.setLoading(true)
        binding.btnSearch.setVisible(false)
        bindText()
        bindRecyclerView()
    }

    private fun bindText() {
        binding.apply {
            tvTitle.text = txtString(R.string.top_chef)
            tvDescription.text = txtString(R.string.collection_description)
        }
    }

    private fun bindRecyclerView() {
        viewAllChefAdapter = VAChefAdapter { chef ->
            goToChefDetail(chef)
        }
        binding.rvContent.apply {
            adapter = viewAllChefAdapter
            layoutManager = getRecyclerviewLayoutManager()
        }
    }

    override fun initListener() {
        binding.apply {
            btnBack.onClick {
                onBackPressed()
            }
        }
        viewAllChefAdapter.addLoadStateListener { combinedLoadStates ->
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
                    getChefsPaging().collect { chefsPaging ->
                        if (::viewAllChefAdapter.isInitialized &&
                            chefsPaging != null
                        ) {
                            viewAllChefAdapter.submitData(chefsPaging)
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

    private fun goToChefDetail(chef: AuthorDto) {
        ChefDetailActivity.startActivity(this) {
            putExtra(ChefDetailActivity.KEY_CHEF_DTO, chef)
        }
    }

    companion object {
        fun startActivity(context: Context?, configIntent: Intent.() -> Unit) {
            context?.let {
                it.start<VAChefActivity> {
                    apply(configIntent)
                }
            }
        }
    }

}
