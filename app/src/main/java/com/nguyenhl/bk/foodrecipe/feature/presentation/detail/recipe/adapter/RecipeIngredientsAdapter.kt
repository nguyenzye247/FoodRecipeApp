package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.recipe.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nguyenhl.bk.foodrecipe.core.extension.views.loadImage
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.databinding.ItemRecipeDetailIngredientBinding
import com.nguyenhl.bk.foodrecipe.feature.dto.IngredientDto

class RecipeIngredientsAdapter(
    private val ingredients: List<IngredientDto>,
    private val onItemClick: (ingredient: IngredientDto) -> Unit
) :
    RecyclerView.Adapter<RecipeIngredientsAdapter.RecipeIngredientHolder>() {

    inner class RecipeIngredientHolder(val binding: ItemRecipeDetailIngredientBinding) :
        ViewHolder(binding.root) {

        fun bind(ingredient: IngredientDto) {
            binding.apply {
                ivRecipeDetailIngredient.loadImage(ingredient.imageUrl)
                tvRecipeDetailIngredient.text = ingredient.name

                root.onClick {
                    onItemClick(ingredient)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeIngredientHolder {
        return RecipeIngredientHolder(
            ItemRecipeDetailIngredientBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecipeIngredientHolder, position: Int) {
        holder.bind(ingredients[position])
    }

    override fun getItemCount(): Int = ingredients.size
}