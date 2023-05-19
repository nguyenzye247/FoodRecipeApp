package com.nguyenhl.bk.foodrecipe.feature.presentation.dishprefered

import androidx.activity.viewModels
import com.nguyenhl.bk.foodrecipe.databinding.ActivityDishPreferredBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.ViewModelProviderFactory

class ActivityDishPreferred : BaseActivity<ActivityDishPreferredBinding, DishPreferredViewModel>() {
    override fun getLazyBinding() = lazy { ActivityDishPreferredBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = viewModels<DishPreferredViewModel> {
        ViewModelProviderFactory(BaseInput.DishPreferredInput(application))
    }

    override fun initViews() {

    }

    override fun initListener() {

    }

    override fun initObservers() {

    }
}
