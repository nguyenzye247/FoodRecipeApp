package com.nguyenhl.bk.foodrecipe.feature.presentation.main.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nguyenhl.bk.foodrecipe.core.extension.views.loadImage
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.databinding.ItemRecipeByDateBinding
import com.nguyenhl.bk.foodrecipe.feature.dto.calendar.RecipeByDateDto

class RecipeByDateAdapter(
    private val recipes: List<RecipeByDateDto>,
    private val onItemClick: (recipe: RecipeByDateDto) -> Unit,
    private val onFavoriteClick: (recipe: RecipeByDateDto) -> Unit,
    private val onRemoveClick: (recipe: RecipeByDateDto) -> Unit,
) : RecyclerView.Adapter<RecipeByDateAdapter.RecipeByDateHolder>() {

    inner class RecipeByDateHolder(val binding: ItemRecipeByDateBinding) :
        ViewHolder(binding.root) {

        fun bind(recipeByDate: RecipeByDateDto) {
            binding.apply {
                ivBackground.loadImage(recipeByDate.recipe.imageUrl)
                tvChefName.text = recipeByDate.recipe.author
                tvRecipeName.text = recipeByDate.recipe.name
                tvCookTime.apply {
                    val cookTimeText = "${recipeByDate.recipe.totalTime} min"
                    text = cookTimeText
                }

                root.onClick {
                    onItemClick(recipeByDate)
                }
                btnFavorite.apply {
                    isSelected = recipeByDate.recipe.isLiked
                    onClick {
                        isSelected = !isSelected
                        recipeByDate.recipe.isLiked = isSelected
                        onFavoriteClick(recipeByDate)
                    }
                }
                btnRemove.onClick {
                    onRemoveClick(recipeByDate)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeByDateHolder {
        return RecipeByDateHolder(
            ItemRecipeByDateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecipeByDateHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun getItemCount(): Int = recipes.size

    fun notifyChanges(oldList: List<RecipeByDateDto>) {
        val diff = DiffUtil.calculateDiff(
            RecipeByMealTypeDiffCallback(
                oldList,
                recipes
            )
        )
        diff.dispatchUpdatesTo(this)
    }
}
