package com.nguyenhl.bk.foodrecipe.feature.presentation.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nguyenhl.bk.foodrecipe.core.extension.views.loadImage
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.databinding.ItemVaRecipeBinding
import com.nguyenhl.bk.foodrecipe.feature.dto.RecipeDto

class RecipeAdapter(
    private val onItemClick: (recipe: RecipeDto) -> Unit
) : PagingDataAdapter<RecipeDto, RecipeAdapter.VASuggestHolder>(COMPARATOR) {

    inner class VASuggestHolder(val binding: ItemVaRecipeBinding) : ViewHolder(binding.root) {
        fun bind(recipe: RecipeDto) {
            binding.apply {
                ivBackground.loadImage(recipe.imageUrl)
                tvRecipeName.text = recipe.name
                val cookTimeText = "${recipe.totalTime} min"
                tvCookTime.text = cookTimeText
                tvChefName.text = recipe.author

                root.onClick {
                    onItemClick(recipe)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: VASuggestHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VASuggestHolder {
        return VASuggestHolder(
            ItemVaRecipeBinding.inflate(
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

            override fun areContentsTheSame(oldItem: RecipeDto, newItem: RecipeDto): Boolean {
                return oldItem.idRecipe == newItem.idRecipe &&
                        oldItem.apiId == newItem.apiId &&
                        oldItem.idRecipeDetail == newItem.idRecipeDetail
            }
        }
    }
}
