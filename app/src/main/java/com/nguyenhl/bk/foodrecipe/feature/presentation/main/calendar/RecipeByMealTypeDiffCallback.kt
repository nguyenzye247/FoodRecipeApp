package com.nguyenhl.bk.foodrecipe.feature.presentation.main.calendar

import androidx.recyclerview.widget.DiffUtil
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto

class RecipeByMealTypeDiffCallback(
    private var oldList: List<RecipeDto>,
    private var newList: List<RecipeDto>
): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].idRecipe == newList[newItemPosition].idRecipe
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.idRecipe == newItem.idRecipe &&
                oldItem.apiId == newItem.apiId &&
                oldItem.idRecipeDetail == newItem.idRecipeDetail &&
                oldItem.isLiked == newItem.isLiked
    }

}
