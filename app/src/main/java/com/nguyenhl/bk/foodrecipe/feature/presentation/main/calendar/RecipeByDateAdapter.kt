package com.nguyenhl.bk.foodrecipe.feature.presentation.main.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nguyenhl.bk.foodrecipe.core.extension.views.loadImage
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.databinding.ItemRecipeByDateBinding
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto

class RecipeByDateAdapter(
    private val recipes: List<RecipeDto>,
    private val onItemClick: (recipe: RecipeDto) -> Unit,
    private val onFavoriteClick: (recipe: RecipeDto) -> Unit,
): RecyclerView.Adapter<RecipeByDateAdapter.RecipeByDateHolder>() {

    inner class RecipeByDateHolder(val binding: ItemRecipeByDateBinding) :
        ViewHolder(binding.root) {

        fun bind(recipe: RecipeDto) {
            binding.apply {
                ivBackground.loadImage(recipe.imageUrl)
                tvChefName.text = recipe.author
                tvRecipeName.text = recipe.name
                tvCookTime.apply {
                    val cookTimeText = "${recipe.totalTime} min"
                    text = cookTimeText
                }

                root.onClick {
                    onItemClick(recipe)
                }
                btnFavorite.apply {
                    isSelected = recipe.isLiked
                    onClick {
                        isSelected = !isSelected
                        recipe.isLiked = isSelected
                        onFavoriteClick(recipe)
                    }
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

    fun notifyChanges(oldList: List<RecipeDto>) {
        val diff = DiffUtil.calculateDiff(
            RecipeByMealTypeDiffCallback(
                oldList,
                recipes
            )
        )
        diff.dispatchUpdatesTo(this)
    }
}
