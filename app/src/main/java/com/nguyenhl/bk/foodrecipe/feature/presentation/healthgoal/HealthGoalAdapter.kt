package com.nguyenhl.bk.foodrecipe.feature.presentation.healthgoal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nguyenhl.bk.foodrecipe.databinding.ItemHealthGoalBinding
import com.nguyenhl.bk.foodrecipe.feature.dto.healthgoal.HealthGoalDto

class HealthGoalAdapter(
    private val healthGoals: List<HealthGoalDto>
): RecyclerView.Adapter<HealthGoalAdapter.HealthGoalItemHolder>() {

    inner class HealthGoalItemHolder(val binding: ItemHealthGoalBinding): ViewHolder(binding.root) {

        fun bind(healthGoal: HealthGoalDto) {
            binding.apply {
                tvStartDayValue
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealthGoalItemHolder {
        return HealthGoalItemHolder(
            ItemHealthGoalBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: HealthGoalItemHolder, position: Int) {
        holder.bind(healthGoals[position])
    }

    override fun getItemCount(): Int = healthGoals.size
}
