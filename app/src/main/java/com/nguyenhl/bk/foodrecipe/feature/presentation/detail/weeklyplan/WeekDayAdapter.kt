package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.weeklyplan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nguyenhl.bk.foodrecipe.R
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.databinding.ItemWeekdayBinding
import com.nguyenhl.bk.foodrecipe.feature.dto.enumdata.WeekDay

class WeekDayAdapter(
    private val weekdays: List<WeekDay>,
    private val onItemClick: (weekday: WeekDay) -> Unit
): RecyclerView.Adapter<WeekDayAdapter.WeekDayItemHolder>() {

    inner class WeekDayItemHolder(val binding: ItemWeekdayBinding): ViewHolder(binding.root) {

        fun bind(weekday: WeekDay) {
            binding.apply {
                if (weekday.isSelected) {
                    tvWeekday.setBackgroundResource(R.drawable.bg_weekday_selected)
                } else {
                    tvWeekday.background = null
                }

                tvWeekday.apply {
                    isSelected = weekday.isSelected
                    text = weekday.value
                }

                root.onClick {
                    weekdays.onEach { it.isSelected = false }
                    tvWeekday.isSelected = true
                    weekday.isSelected = true
                    onItemClick(weekday)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekDayItemHolder {
        return WeekDayItemHolder(
            ItemWeekdayBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WeekDayItemHolder, position: Int) {
        holder.bind(weekdays[position])
    }

    override fun getItemCount(): Int = weekdays.size
}
