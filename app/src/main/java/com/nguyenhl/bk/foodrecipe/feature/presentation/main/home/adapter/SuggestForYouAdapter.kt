package com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nguyenhl.bk.foodrecipe.R
import com.nguyenhl.bk.foodrecipe.core.extension.resources.drawable
import com.nguyenhl.bk.foodrecipe.core.extension.views.imageDrawable
import com.nguyenhl.bk.foodrecipe.core.extension.views.loadImage
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.databinding.ItemSuggestForYouBinding
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto

class SuggestForYouAdapter(
    private val suggestRecipes: List<RecipeDto>,
    private val onFavorite: (recipe: RecipeDto) -> Unit
) : RecyclerView.Adapter<SuggestForYouAdapter.SuggestForYouViewHolder>() {


    inner class SuggestForYouViewHolder(val binding: ItemSuggestForYouBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recipe: RecipeDto) {
            binding.apply {
                ivBackground.loadImage(recipe.imageUrl)
                tvChefName.text = recipe.author
                tvRecipeName.text = recipe.name
                tvCookTime.text = recipe.totalTime.toString()
                setFavourite(recipe.isLiked)

                btnFavorite.onClick {
                    onFavorite.invoke(recipe)
                }
            }
        }

        private fun setFavourite(isFavorite: Boolean) {
            binding.btnFavorite.apply {
                imageDrawable = if (isFavorite) {
                    drawable(R.drawable.ic_favorite_selected)
                } else {
                    drawable(R.drawable.ic_favorite_selected)
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
        holder.bind(suggestRecipes[position])
    }

    override fun getItemCount(): Int = suggestRecipes.size
}
