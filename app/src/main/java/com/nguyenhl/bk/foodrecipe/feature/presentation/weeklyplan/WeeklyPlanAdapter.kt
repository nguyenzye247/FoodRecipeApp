package com.nguyenhl.bk.foodrecipe.feature.presentation.weeklyplan

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nguyenhl.bk.foodrecipe.core.extension.views.loadImage
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.databinding.ItemWeeklyPlanBinding
import com.nguyenhl.bk.foodrecipe.feature.dto.weeklyplan.WeeklyPlanDto

class WeeklyPlanAdapter(
    private val weeklyPlans: List<WeeklyPlanDto>,
    private val onItemClick: (weeklyPlan: WeeklyPlanDto) -> Unit
): RecyclerView.Adapter<WeeklyPlanAdapter.WeeklyPlanItemHolder>() {

    inner class WeeklyPlanItemHolder(val binding: ItemWeeklyPlanBinding) :
        ViewHolder(binding.root) {

        fun bind(weeklyPlan: WeeklyPlanDto) {
            binding.apply {
                ivBackground.loadImage(weeklyPlan.imageUrl)
                tvPlanName.text = weeklyPlan.name

                root.onClick {
                    onItemClick(weeklyPlan)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeklyPlanItemHolder {
        return WeeklyPlanItemHolder(
            ItemWeeklyPlanBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WeeklyPlanItemHolder, position: Int) {
        holder.bind(weeklyPlans[position])
    }

    override fun getItemCount(): Int = weeklyPlans.size
}
