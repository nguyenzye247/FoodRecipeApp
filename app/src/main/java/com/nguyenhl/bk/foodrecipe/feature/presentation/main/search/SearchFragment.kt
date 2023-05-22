package com.nguyenhl.bk.foodrecipe.feature.presentation.main.search

import androidx.fragment.app.activityViewModels
import com.nguyenhl.bk.foodrecipe.databinding.FragmentSearchBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseFragment
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.MainViewModel

class SearchFragment : BaseFragment<FragmentSearchBinding, MainViewModel>() {
    override fun getLazyBinding() = lazy { FragmentSearchBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = activityViewModels<MainViewModel>()

    override fun setupInit() {

    }
}