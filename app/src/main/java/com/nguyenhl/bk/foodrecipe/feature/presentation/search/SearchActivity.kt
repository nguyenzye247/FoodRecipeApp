package com.nguyenhl.bk.foodrecipe.feature.presentation.search

import android.content.Context
import android.content.Intent
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.databinding.ActivitySearchBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.MainActivity
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SearchActivity: BaseActivity<ActivitySearchBinding, SearchViewModel>() {
    override fun getLazyBinding() = lazy { ActivitySearchBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModel<SearchViewModel> {
        parametersOf(BaseInput.SearchInput(
            application
        ))
    }

    override fun initViews() {

    }

    override fun initListener() {

    }

    override fun initObservers() {

    }

    companion object {
        fun startActivity(context: Context?, configIntent: Intent.() -> Unit) {
            context?.let {
                it.start<SearchActivity> {
                    apply(configIntent)
                }
            }
        }
    }
}
