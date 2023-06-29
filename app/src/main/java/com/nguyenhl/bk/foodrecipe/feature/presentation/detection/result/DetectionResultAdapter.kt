package com.nguyenhl.bk.foodrecipe.feature.presentation.detection.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.databinding.ItemDetectionResultBinding

class DetectionResultAdapter(
    private val ingredients: List<String>,
    private val onItemClick: (ingredient: String) -> Unit
) : RecyclerView.Adapter<DetectionResultAdapter.DetectionResultItemHolder>() {

    inner class DetectionResultItemHolder(val binding: ItemDetectionResultBinding) :
        ViewHolder(binding.root) {

        fun bind(ingredient: String) {
            binding.apply {
                tvIngredient.text = ingredient.replace("_", " ")

                root.onClick {
                    onItemClick(ingredient)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetectionResultItemHolder {
        return DetectionResultItemHolder(
            ItemDetectionResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = ingredients.size

    override fun onBindViewHolder(holder: DetectionResultItemHolder, position: Int) {
        holder.bind(ingredients[position])
    }
}
