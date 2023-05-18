package com.nguyenhl.bk.foodrecipe.feature.presentation.main

import androidx.activity.viewModels
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.databinding.ActivityMainBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.ViewModelProviderFactory

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun getLazyBinding() = lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModels<MainViewModel> {
        ViewModelProviderFactory(BaseInput.MainInput(application))
    }

    override fun initViews() {

    }

    override fun initListener() {

    }

    override fun initObservers() {

    }
}
