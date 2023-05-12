package com.nguyenhl.bk.foodrecipe.feature.ui.main

import androidx.activity.viewModels
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.databinding.ActivityMainBinding
import com.nguyenhl.bk.foodrecipe.extension.observe

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun getLazyBinding() = lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModels<MainViewModel>()

    override fun setupInit() {
        TODO("Not yet implemented")
    }
}
