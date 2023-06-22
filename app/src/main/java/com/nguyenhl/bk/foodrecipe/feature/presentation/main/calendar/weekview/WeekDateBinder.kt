package com.nguyenhl.bk.foodrecipe.feature.presentation.main.calendar.weekview

import android.view.View
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.view.ViewContainer
import com.kizitonwose.calendar.view.WeekDayBinder
import com.nguyenhl.bk.foodrecipe.R
import com.nguyenhl.bk.foodrecipe.core.extension.displayText
import com.nguyenhl.bk.foodrecipe.core.extension.resources.getColor
import com.nguyenhl.bk.foodrecipe.core.extension.views.setVisible
import com.nguyenhl.bk.foodrecipe.databinding.ItemDayWeekCalendarBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class WeekDateBinder(
    private var selectedDate: LocalDate,
    private val onDateClick: (oldDate: LocalDate, weekDay: WeekDay) -> Unit
): WeekDayBinder<WeekDateBinder.DayViewContainer> {
    private val dateFormatter = DateTimeFormatter.ofPattern("dd")
    private val today = LocalDate.now()

    inner class DayViewContainer(view: View) : ViewContainer(view) {
        val binding = ItemDayWeekCalendarBinding.bind(view)
        private lateinit var day: WeekDay

        init {
            view.setOnClickListener {
                if (selectedDate != day.date) {
                    val oldDate = selectedDate
                    selectedDate = day.date
                    onDateClick(oldDate, day)
                    setSelected()
                }
            }
        }

        fun bind(day: WeekDay) {
            this.day = day
            binding.apply {
                tvCalendarWeekday.text = day.date.dayOfWeek.displayText()
                tvCalendarDay.text = dateFormatter.format(day.date)

                if (day.date.isEqual(selectedDate)) {
                    bindDaySelected()
                } else {
                    bindDayUnselected()
                }

                if (
                    !day.date.isAfter(today) &&
                    !day.date.isBefore(today) &&
                    !today.isEqual(selectedDate)
                ) {
                    llDate.setBackgroundResource(R.drawable.bg_today)
                }
            }
        }

        private fun bindDaySelected() {
            binding.apply {
                indicatorView.apply {
                    setVisible(true)
                    isSelected = true
                }
                tvCalendarDay.apply {
                    setTextColor(getColor(R.color.white))
                }
                tvCalendarWeekday.apply {
                    setTextColor(getColor(R.color.white))
                }
                llDate.apply {
                    setBackgroundResource(R.drawable.bg_date_item_selected)
                }
            }
        }

        private fun bindDayUnselected() {
            binding.apply {
                indicatorView.apply {
                    setVisible(false)
                    isSelected = false
                }
                tvCalendarDay.apply {
                    setTextColor(getColor(R.color.black))
                }
                tvCalendarWeekday.apply {
                    setTextColor(getColor(R.color.black))
                }
                llDate.apply {
                    setBackgroundResource(R.drawable.bg_date_item_normal)
                }
            }
        }

        private fun setSelected() {
            binding.indicatorView.isSelected = true
        }
    }

    override fun bind(container: DayViewContainer, data: WeekDay) {
        container.bind(data)
    }

    override fun create(view: View): DayViewContainer {
        return DayViewContainer(view)
    }
}