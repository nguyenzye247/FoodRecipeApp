package com.nguyenhl.bk.foodrecipe.feature.presentation.createdishprefered.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nguyenhl.bk.foodrecipe.core.extension.views.loadImage
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.core.extension.views.setVisible
import com.nguyenhl.bk.foodrecipe.databinding.ItemDishPreferredBinding
import com.nguyenhl.bk.foodrecipe.feature.dto.DishPreferredDto

class DishPreferredAdapter(
    private val preferredDishes: List<DishPreferredDto>,
    private val onDishSelected: (dish: DishPreferredDto) -> Unit
) : RecyclerView.Adapter<DishPreferredAdapter.PreferredDishViewHolder>() {

    inner class PreferredDishViewHolder(val binding: ItemDishPreferredBinding) :
        ViewHolder(binding.root) {
        fun bind(dish: DishPreferredDto) {
            binding.flSelected.setVisible(dish.isSelected)
            binding.apply {
                ivDishPreferred.loadImage(dish.imageUrl)
                tvDishPreferred.text = dish.name
            }
            binding.root.onClick {
                dish.isSelected = !dish.isSelected
                binding.flSelected.setVisible(dish.isSelected)
                onDishSelected.invoke(dish)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PreferredDishViewHolder {
        return PreferredDishViewHolder(
            ItemDishPreferredBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PreferredDishViewHolder, position: Int) {
        holder.bind(preferredDishes[position])
    }

    override fun getItemCount(): Int = preferredDishes.size
}