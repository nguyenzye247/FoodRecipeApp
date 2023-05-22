package com.nguyenhl.bk.foodrecipe.feature.presentation.auth.createaccount

import androidx.activity.viewModels
import com.nguyenhl.bk.foodrecipe.databinding.ActivityCreateAccountBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.ViewModelProviderFactory

class CreateAccountActivity: BaseActivity<ActivityCreateAccountBinding, CreateAccountViewModel>() {
    override fun getLazyBinding() = lazy { ActivityCreateAccountBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModels<CreateAccountViewModel> {
        ViewModelProviderFactory(BaseInput.CreateAccountInput(application))
    }

    override fun initViews() {

    }

    override fun initListener() {

    }

    override fun initObservers() {

    }

}
