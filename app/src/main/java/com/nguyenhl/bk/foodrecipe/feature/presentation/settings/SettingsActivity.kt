package com.nguyenhl.bk.foodrecipe.feature.presentation.settings

import android.content.Context
import android.content.Intent
import com.nguyenhl.bk.foodrecipe.core.extension.start
import com.nguyenhl.bk.foodrecipe.databinding.ActivitySettingsBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SettingsActivity : BaseActivity<ActivitySettingsBinding, SettingsViewModel>() {
    override fun getLazyBinding() = lazy { ActivitySettingsBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModel<SettingsViewModel> {
        parametersOf(BaseInput.SettingsInput(application))
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
                it.start<SettingsActivity> {
                    apply(configIntent)
                }
            }
        }
    }
}