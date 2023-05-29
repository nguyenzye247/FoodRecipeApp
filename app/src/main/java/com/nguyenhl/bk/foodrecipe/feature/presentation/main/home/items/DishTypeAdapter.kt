package com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nguyenhl.bk.foodrecipe.databinding.ItemDishTypeBinding
import com.nguyenhl.bk.foodrecipe.feature.dto.DishPreferredDto

class DishTypeAdapter(
    private val dishTypes: List<DishPreferredDto>
) : RecyclerView.Adapter<DishTypeAdapter.DishTypeViewHolder>() {

    inner class DishTypeViewHolder(val binding: ItemDishTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dishType: DishPreferredDto) {
            binding.apply {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishTypeViewHolder {
        return DishTypeViewHolder(
            ItemDishTypeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DishTypeViewHolder, position: Int) {
        holder.bind(dishTypes[position])
    }

    override fun getItemCount(): Int = dishTypes.size
}
