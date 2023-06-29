package com.nguyenhl.bk.foodrecipe.feature.presentation.detection.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nguyenhl.bk.foodrecipe.core.extension.views.loadImage
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.databinding.ItemRecipeDetailIngredientBinding
import com.nguyenhl.bk.foodrecipe.feature.dto.IngredientDto

class DetectionResultAdapter(
    private val ingredients: List<IngredientDto>,
    private val onItemClick: (ingredient: IngredientDto) -> Unit
) : RecyclerView.Adapter<DetectionResultAdapter.DetectionResultItemHolder>() {

    inner class DetectionResultItemHolder(val binding: ItemRecipeDetailIngredientBinding) :
        ViewHolder(binding.root) {

        fun bind(ingredient: IngredientDto) {
            binding.apply {
                tvRecipeDetailIngredient.text = ingredient.name
                ivRecipeDetailIngredient.loadImage(ingredient.imageUrl)

                root.onClick {
                    onItemClick(ingredient)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetectionResultItemHolder {
        return DetectionResultItemHolder(
            ItemRecipeDetailIngredientBinding.inflate(
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
