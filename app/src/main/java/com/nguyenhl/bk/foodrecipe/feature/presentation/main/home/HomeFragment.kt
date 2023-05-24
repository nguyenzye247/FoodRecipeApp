package com.nguyenhl.bk.foodrecipe.feature.presentation.main.home

import androidx.fragment.app.activityViewModels
import com.nguyenhl.bk.foodrecipe.databinding.FragmentHomeBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseFragment
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.MainViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.items.DishTypeAdapter
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.items.SuggestForYouAdapter

class HomeFragment : BaseFragment<FragmentHomeBinding, MainViewModel>() {
    private lateinit var dishTypeAdapter: DishTypeAdapter
    private lateinit var suggestForYouAdapter: SuggestForYouAdapter


    override fun getLazyBinding() = lazy { FragmentHomeBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = activityViewModels<MainViewModel>()

    override fun initViews() {
        binding.apply {

        }
    }

    override fun initListener() {

    }

    override fun initObservers() {

    }

}
