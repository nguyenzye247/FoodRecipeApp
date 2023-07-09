package com.nguyenhl.bk.foodrecipe.feature.presentation.main.profile.fragment

import androidx.fragment.app.activityViewModels
import com.nguyenhl.bk.foodrecipe.databinding.FragmentProfileHealthStatusBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseFragment
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.MainViewModel
import com.nguyenhl.bk.foodrecipe.feature.util.AppUtil
import com.nguyenhl.bk.foodrecipe.feature.util.DateFormatUtil.getAgeFrom

class ProfileHealthStatusFragment: BaseFragment<FragmentProfileHealthStatusBinding, MainViewModel>() {


    override fun getLazyBinding() = lazy { FragmentProfileHealthStatusBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = activityViewModels<MainViewModel>()

    override fun initViews() {
        bindUserHealthInfoView()
    }

    override fun initListener() {

    }

    override fun initObservers() {

    }

    private fun bindUserHealthInfoView() {
        val userInfo = viewModel.getUserInfo() ?: return
        val bmi = AppUtil.calculateBMI(
            getAgeFrom(userInfo.dob),
            userInfo.height,
            userInfo.weight
        )

        val bmr = AppUtil.calculateBMR(
            userInfo.weight,
            userInfo.height,
            getAgeFrom(userInfo.dob),
            userInfo.gender == 1
        )

        binding.apply {
            tvBmiValue.text = bmi.toString()
            tvBmiLevel.text = AppUtil.getBmiLevel(bmi).value

            tvCalorieIntakeValue.text = bmr.toString()
        }
    }

    companion object {
        const val CALORIES_DEFAULT = "2000"

        fun newInstance(): ProfileHealthStatusFragment = ProfileHealthStatusFragment()
    }
}
