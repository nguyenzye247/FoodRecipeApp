package com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.ingredient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nguyenhl.bk.foodrecipe.core.extension.views.loadImage
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.databinding.ItemVaIngredientBinding
import com.nguyenhl.bk.foodrecipe.feature.dto.IngredientDto

class VAIngredientAdapter(
    private val onItemClick: (ingredient: IngredientDto) -> Unit
) : PagingDataAdapter<IngredientDto, VAIngredientAdapter.VAIngredientHolder>(COMPARATOR) {

    inner class VAIngredientHolder(val binding: ItemVaIngredientBinding) :
        ViewHolder(binding.root) {
        fun bind(ingredient: IngredientDto) {
            binding.apply {
                ivBackground.loadImage(ingredient.imageUrl)
                tvIngredientName.text = ingredient.name

                root.onClick {
                    onItemClick(ingredient)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: VAIngredientHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VAIngredientHolder {
        return VAIngredientHolder(
            ItemVaIngredientBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<IngredientDto>() {
            override fun areItemsTheSame(oldItem: IngredientDto, newItem: IngredientDto): Boolean {
                return oldItem.idIngredient == newItem.idIngredient
            }

            override fun areContentsTheSame(
                oldItem: IngredientDto,
                newItem: IngredientDto
            ): Boolean {
                return oldItem.idIngredient == newItem.idIngredient &&
                        oldItem.idIngredientDetail == newItem.idIngredientDetail
            }
        }
    }
}
