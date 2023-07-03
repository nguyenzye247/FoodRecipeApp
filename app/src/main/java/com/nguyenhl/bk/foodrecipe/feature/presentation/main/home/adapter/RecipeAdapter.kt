package com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nguyenhl.bk.foodrecipe.core.extension.views.loadImage
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.databinding.ItemSuggestForYouBinding
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto

class RecipeAdapter(
    private val recipes: List<RecipeDto>,
    private val onItemClick: (recipe: RecipeDto) -> Unit,
    private val onFavoriteClick: (recipe: RecipeDto) -> Unit
) : RecyclerView.Adapter<RecipeAdapter.SuggestForYouViewHolder>() {

    inner class SuggestForYouViewHolder(val binding: ItemSuggestForYouBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: RecipeDto) {
            binding.apply {
                ivBackground.loadImage(recipe.imageUrl)
                tvChefName.text = recipe.author
                tvRecipeName.text = recipe.name
                tvCookTime.apply{
                    val timeCookInMinuteText = "${recipe.totalTime} min"
                    text = timeCookInMinuteText
                }

                root.onClick {
                    onItemClick.invoke(recipe)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestForYouViewHolder {
        return SuggestForYouViewHolder(
            ItemSuggestForYouBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SuggestForYouViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    override fun getItemCount(): Int = recipes.size
}
