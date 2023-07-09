package com.nguyenhl.bk.foodrecipe.feature.presentation.detail.healthgoal

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nguyenhl.bk.foodrecipe.core.extension.views.loadImage
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.databinding.ItemDailySuggestRecipeBinding
import com.nguyenhl.bk.foodrecipe.feature.dto.healthgoal.MealSuggestDto

class HealthGoalMealSuggestRecipeAdapter(
    private val suggestRecipes: List<MealSuggestDto>,
    private val onItemClick: (recipe: MealSuggestDto) -> Unit
) : RecyclerView.Adapter<HealthGoalMealSuggestRecipeAdapter.ItemDailySuggestRecipeHolder>() {

    inner class ItemDailySuggestRecipeHolder(val binding: ItemDailySuggestRecipeBinding) :
        ViewHolder(binding.root) {
        fun bind(meal: MealSuggestDto) {
            val recipe = meal.recipe
            binding.apply {
                ivBackground.loadImage(recipe.imageUrl)
                tvRecipeName.text = recipe.name

                tvCaloriesCountValue.text = meal.calories.toString()
                tvMealType.text = meal.type

                root.onClick {
                    onItemClick(meal)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemDailySuggestRecipeHolder {
        return ItemDailySuggestRecipeHolder(
            ItemDailySuggestRecipeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemDailySuggestRecipeHolder, position: Int) {
        holder.bind(suggestRecipes[position])
    }

    override fun getItemCount(): Int = suggestRecipes.size

}
