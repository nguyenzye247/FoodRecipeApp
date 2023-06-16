package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nguyenhl.bk.foodrecipe.core.extension.views.loadImage
import com.nguyenhl.bk.foodrecipe.databinding.ItemRecipeDetailNutrientBinding
import com.nguyenhl.bk.foodrecipe.feature.dto.NutrientDto

class RecipeNutrientAdapter(
    private val nutrients: List<NutrientDto>
) :
    RecyclerView.Adapter<RecipeNutrientAdapter.RecipeNutrientHolder>() {

    inner class RecipeNutrientHolder(val binding: ItemRecipeDetailNutrientBinding) :
        ViewHolder(binding.root) {

        fun bind(nutrient: NutrientDto) {
            binding.apply {
                ivNutrient.loadImage(nutrient.nutrientDetail.imageUrl)
                tvNutrientValue.text = nutrient.value.toString()
                tvNutrientUnit.text = nutrient.nutrientDetail.unit
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecipeNutrientAdapter.RecipeNutrientHolder {
        return RecipeNutrientHolder(
            ItemRecipeDetailNutrientBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: RecipeNutrientAdapter.RecipeNutrientHolder,
        position: Int
    ) {
        holder.bind(nutrients[position])
    }

    override fun getItemCount(): Int = nutrients.size
}
