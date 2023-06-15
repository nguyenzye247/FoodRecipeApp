package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.direction

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nguyenhl.bk.foodrecipe.databinding.ItemRecipeDirectionsDetailBinding

class RecipeDirectionDetailsAdapter(
    private val directions: List<String>
) :
    RecyclerView.Adapter<RecipeDirectionDetailsAdapter.RecipeDirectionItemHolder>() {

    inner class RecipeDirectionItemHolder(val binding: ItemRecipeDirectionsDetailBinding) :
        ViewHolder(binding.root) {
        fun bind(direction: String) {
            binding.apply {
                tvIngredientDetailsNumb.apply {
                    val numb = "${absoluteAdapterPosition + 1}."
                    text = numb
                }
                tvIngredientDetails.text = direction
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeDirectionItemHolder {
        return RecipeDirectionItemHolder(
            ItemRecipeDirectionsDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecipeDirectionItemHolder, position: Int) {
        holder.bind(directions[position])
    }

    override fun getItemCount(): Int = directions.size
}
