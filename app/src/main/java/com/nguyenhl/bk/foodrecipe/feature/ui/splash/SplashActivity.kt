package com.nguyenhl.bk.foodrecipe.feature.ui.splash

import androidx.activity.viewModels
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    override fun getLazyBinding() = lazy { ActivitySplashBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModels<SplashViewModel>()

    override fun setupInit() {
        TODO("Not yet implemented")
    }

}