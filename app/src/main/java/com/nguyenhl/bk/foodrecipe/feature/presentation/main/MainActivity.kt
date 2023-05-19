package com.nguyenhl.bk.foodrecipe.feature.presentation.main

import android.content.Context
import android.content.Intent
import androidx.activity.viewModels
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.databinding.ActivityMainBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.ViewModelProviderFactory
import com.nguyenhl.bk.foodrecipe.feature.presentation.authentication.createinfo.CreateInfoActivity

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

    companion object {
        fun startActivity(context: Context?, configIntent: Intent.() -> Unit) {
            context?.let {
                it.start<MainActivity> {
                    apply(configIntent)
                }
            }
        }
    }
}
