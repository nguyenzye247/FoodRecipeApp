package com.nguyenhl.bk.foodrecipe.feature.presentation.main.calendar

import androidx.fragment.app.activityViewModels
import com.nguyenhl.bk.foodrecipe.databinding.FragmentCalendarBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseFragment
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.MainViewModel

class CalendarFragment : BaseFragment<FragmentCalendarBinding, MainViewModel>() {
    override fun getLazyBinding() = lazy { FragmentCalendarBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = activityViewModels<MainViewModel>()

    override fun setupInit() {

    }

}
