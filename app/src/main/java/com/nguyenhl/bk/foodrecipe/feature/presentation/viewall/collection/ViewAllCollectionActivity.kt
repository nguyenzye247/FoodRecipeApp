package com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.collection

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.nguyenhl.bk.foodrecipe.R
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.ObsoleteSplittiesLifecycleApi
import com.nguyenhl.bk.foodrecipe.core.extension.livedata.observe
import com.nguyenhl.bk.foodrecipe.core.extension.resources.txtString
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.core.extension.views.setVisible
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.dto.CollectionDto
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.BaseViewAllActivity
import com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.ViewAllContentType
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ViewAllCollectionActivity : BaseViewAllActivity<ViewAllCollectionViewModel>() {
    private lateinit var viewAllCollectionAdapter: ViewAllCollectionAdapter

    override fun getLazyViewModel() = viewModel<ViewAllCollectionViewModel> {
        parametersOf(BaseInput.BaseViewAllInput.ViewAllCollectionInput(application))
    }

    override fun getRecyclerviewLayoutManager(): LayoutManager {
        return StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL
        )
    }

    override fun getContentType(): ViewAllContentType = ViewAllContentType.COLLECTION

    override fun initViews() {
        viewModel.setLoading(true)
        bindText()
    }

    private fun bindText() {
        binding.apply {
            tvTitle.text = txtString(R.string.collection)
            tvDescription.text = txtString(R.string.collection_description)
        }
    }

    private fun bindRecyclerView(collections: List<CollectionDto>) {
        viewAllCollectionAdapter = ViewAllCollectionAdapter(collections) {

        }
        binding.rvContent.apply {
            adapter = viewAllCollectionAdapter
            layoutManager = getRecyclerviewLayoutManager()
        }
    }

    override fun initListener() {
        binding.apply {
            btnBack.onClick {
                onBackPressed()
            }
        }
    }

    @OptIn(ObsoleteSplittiesLifecycleApi::class)
    override fun initObservers() {
        viewModel.apply {
            observe(liveCollection) { collections ->
                setLoading(false)
                collections?.let {
                    collections.ifEmpty {
                        showEmptyView()
                        return@let
                    }
                    bindRecyclerView(collections)
                } ?: run {
                    showEmptyView()
                }
            }
            observe(liveIsLoading()) { isLoading ->
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
                it.start<ViewAllCollectionActivity> {
                    apply(configIntent)
                }
            }
        }
    }
}
