package com.nguyenhl.bk.foodrecipe.feature.presentation.healthgoal

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nguyenhl.bk.foodrecipe.R
import com.nguyenhl.bk.foodrecipe.core.extension.resources.colorSL
import com.nguyenhl.bk.foodrecipe.core.extension.resources.txtString
import com.nguyenhl.bk.foodrecipe.core.extension.roundFloatToTwoDecimalPlaces
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.databinding.ItemHealthGoalBinding
import com.nguyenhl.bk.foodrecipe.feature.dto.healthgoal.HealthGoalDto
import com.nguyenhl.bk.foodrecipe.feature.util.DateFormatUtil.formatApiDate

class HealthGoalAdapter(
    private val healthGoals: List<HealthGoalDto>,
    private val onItemClick: (healthGoal: HealthGoalDto) -> Unit
): RecyclerView.Adapter<HealthGoalAdapter.HealthGoalItemHolder>() {

    inner class HealthGoalItemHolder(val binding: ItemHealthGoalBinding): ViewHolder(binding.root) {

        @SuppressLint("ResourceAsColor")
        fun bind(healthGoal: HealthGoalDto) {
            binding.apply {
                tvStartDayValue.text = formatApiDate(healthGoal.createdAt, "MMM-dd")
                tvTargetWeightValue.text = healthGoal.targetWeight.roundFloatToTwoDecimalPlaces().toString()
                tvDayGoalValue.text = healthGoal.dayGoal.toString()

                if (healthGoal.isFinished) {
                    ivInProgress.setImageResource(R.drawable.ic_finished)
                    tvInProgress.apply {
                        text = root.context.txtString(R.string.finished)
                    }
                    cvHealthGoalItem.setCardBackgroundColor(
                        root.context.getColor(R.color.rcp_grey_200)
                    )
                } else {
                    ivInProgress.setImageResource(R.drawable.ic_in_progress)
                    tvInProgress.apply {
                        text = root.context.txtString(R.string.in_progress)
                    }
                }

                root.onClick {
                    onItemClick(healthGoal)
                }
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
