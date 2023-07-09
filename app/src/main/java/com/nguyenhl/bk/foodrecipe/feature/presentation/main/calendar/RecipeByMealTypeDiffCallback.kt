package com.nguyenhl.bk.foodrecipe.feature.presentation.main.calendar

import androidx.recyclerview.widget.DiffUtil
import com.nguyenhl.bk.foodrecipe.feature.dto.calendar.RecipeByDateDto

class RecipeByMealTypeDiffCallback(
    private var oldList: List<RecipeByDateDto>,
    private var newList: List<RecipeByDateDto>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].idApi == newList[newItemPosition].idApi
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.mealType == newItem.mealType &&
                oldItem.recipe.idRecipe == newItem.recipe.idRecipe &&
                oldItem.recipe.apiId == newItem.recipe.apiId &&
                oldItem.recipe.idRecipeDetail == newItem.recipe.idRecipeDetail &&
                oldItem.recipe.isLiked == newItem.recipe.isLiked
    }

}
