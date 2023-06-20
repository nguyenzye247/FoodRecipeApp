package com.nguyenhl.bk.foodrecipe.feature.presentation.main.calendar

import androidx.fragment.app.activityViewModels
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.nguyenhl.bk.foodrecipe.core.extension.getWeekPageTitle
import com.nguyenhl.bk.foodrecipe.databinding.FragmentCalendarBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseFragment
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.MainViewModel
import com.nguyenhl.bk.foodrecipe.feature.presentation.main.calendar.weekview.WeekDateBinder
import java.time.LocalDate
import java.time.YearMonth

class CalendarFragment : BaseFragment<FragmentCalendarBinding, MainViewModel>() {
    private var selectedDate = LocalDate.now()

    override fun getLazyBinding() = lazy { FragmentCalendarBinding.inflate(layoutInflater) }

    override fun getLazyViewModel() = activityViewModels<MainViewModel>()

    override fun initViews() {
        adjustScreenSize(binding.tvWeekdayTitle)
        binding.apply {
            weekCalendarView.apply {
                dayBinder = WeekDateBinder(selectedDate) { oldDate, weekDay ->
                    binding.weekCalendarView.notifyDateChanged(weekDay.date)
                    oldDate?.let { binding.weekCalendarView.notifyDateChanged(it) }
                }

                val currentMonth = YearMonth.now()
                setup(
                    currentMonth.minusMonths(5).atStartOfMonth(),
                    currentMonth.plusMonths(5).atEndOfMonth(),
                    firstDayOfWeekFromLocale(),
                )
                scrollToDate(LocalDate.now())
            }
        }
    }

    override fun initListener() {
        binding.apply {
            weekCalendarView.apply {
                weekScrollListener = { weekDays ->
                    binding.tvWeekdayTitle.text = getWeekPageTitle(weekDays)
                }
            }
        }
    }

    override fun initObservers() {

    }
}
