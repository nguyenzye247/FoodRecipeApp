package com.nguyenhl.bk.foodrecipe.feature.presentation.main.profile

import androidx.fragment.app.activityViewModels
import com.nguyenhl.bk.foodrecipe.databinding.FragmentProfileBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseFragment
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.MainViewModel

class ProfileFragment : BaseFragment<FragmentProfileBinding, MainViewModel>() {
    override fun getLazyBinding() = lazy { FragmentProfileBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = activityViewModels<MainViewModel>()

    override fun setupInit() {

    }
}