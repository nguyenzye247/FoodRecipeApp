package com.nguyenhl.bk.foodrecipe.feature.presentation.main.calendar.weekview

import android.view.View
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.view.ViewContainer
import com.kizitonwose.calendar.view.WeekDayBinder
import com.nguyenhl.bk.foodrecipe.core.extension.displayText
import com.nguyenhl.bk.foodrecipe.databinding.ItemDayWeekCalendarBinding
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class WeekDateBinder(
    private var selectedDate: LocalDate,
    private val onDateClick: (oldDate: LocalDate, weekDay: WeekDay) -> Unit
): WeekDayBinder<WeekDateBinder.DayViewContainer> {
    private val dateFormatter = DateTimeFormatter.ofPattern("dd")

    inner class DayViewContainer(view: View) : ViewContainer(view) {
        val binding = ItemDayWeekCalendarBinding.bind(view)
        lateinit var day: WeekDay

        init {
            view.setOnClickListener {
                if (selectedDate != day.date) {
                    val oldDate = selectedDate
                    selectedDate = day.date
                    onDateClick(oldDate, day)
//                    binding.exSevenCalendar.notifyDateChanged(day.date)
//                    oldDate?.let { binding.exSevenCalendar.notifyDateChanged(it) }
                }
            }
        }

        fun bind(day: WeekDay) {
            this.day = day
            binding.apply {
                tvCalendarWeekday.text = dateFormatter.format(day.date)
                tvCalendarDay.text = day.date.dayOfWeek.displayText()
            }
        }

    }

    override fun bind(container: DayViewContainer, data: WeekDay) {
        container.bind(data)
    }

    override fun create(view: View): DayViewContainer {
        return DayViewContainer(view)
    }
}