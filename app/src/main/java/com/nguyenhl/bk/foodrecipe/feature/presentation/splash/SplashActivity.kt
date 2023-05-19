package com.nguyenhl.bk.foodrecipe.feature.presentation.splash

import android.annotation.SuppressLint
import androidx.activity.viewModels
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.databinding.ActivitySplashBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.ViewModelProviderFactory

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    override fun getLazyBinding() = lazy { ActivitySplashBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModels<SplashViewModel> {
        ViewModelProviderFactory(BaseInput.NoInput)
    }

    override fun initViews() {

    }

    override fun initListener() {

    }

    override fun initObservers() {

    }
}