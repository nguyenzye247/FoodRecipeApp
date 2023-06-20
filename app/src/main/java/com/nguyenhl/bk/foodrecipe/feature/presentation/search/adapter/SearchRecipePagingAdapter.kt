package com.nguyenhl.bk.foodrecipe.feature.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nguyenhl.bk.foodrecipe.core.extension.views.loadImage
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.databinding.ItemSearchRecipeBinding
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto

class SearchRecipePagingAdapter(
    private val onItemClick: (recipe: RecipeDto) -> Unit,
    private val onFavoriteClick: (recipe: RecipeDto) -> Unit
) :
    PagingDataAdapter<RecipeDto, SearchRecipePagingAdapter.SearchRecipeItemHolder>(
        COMPARATOR
    ) {

    inner class SearchRecipeItemHolder(val binding: ItemSearchRecipeBinding) :
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

    override fun onBindViewHolder(holder: SearchRecipeItemHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchRecipeItemHolder {
        return SearchRecipeItemHolder(
            ItemSearchRecipeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<RecipeDto>() {
            override fun areItemsTheSame(oldItem: RecipeDto, newItem: RecipeDto): Boolean {
                return oldItem.idRecipe == newItem.idRecipe
            }

            override fun areContentsTheSame(
                oldItem: RecipeDto,
                newItem: RecipeDto
            ): Boolean {
                return oldItem.idRecipe == newItem.idRecipe &&
                        oldItem.idRecipeDetail == newItem.idRecipeDetail &&
                        oldItem.apiId == newItem.apiId &&
                        oldItem.name == newItem.name &&
                        oldItem.imageUrl == newItem.imageUrl &&
                        oldItem.author == newItem.author &&
                        oldItem.isLiked == newItem.isLiked
            }
        }
    }
}
