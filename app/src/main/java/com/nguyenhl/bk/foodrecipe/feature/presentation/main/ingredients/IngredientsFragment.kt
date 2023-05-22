package com.nguyenhl.bk.foodrecipe.feature.presentation.main.ingredients

import androidx.fragment.app.activityViewModels
import com.nguyenhl.bk.foodrecipe.databinding.FragmentIngredientsBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseFragment
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.MainViewModel

class IngredientsFragment : BaseFragment<FragmentIngredientsBinding, MainViewModel>() {
    override fun getLazyBinding() = lazy { FragmentIngredientsBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = activityViewModels<MainViewModel>()

    override fun setupInit() {

    }

}
