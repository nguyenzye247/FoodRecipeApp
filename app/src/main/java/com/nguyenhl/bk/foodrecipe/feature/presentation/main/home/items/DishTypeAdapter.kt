package com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nguyenhl.bk.foodrecipe.databinding.ItemDishTypeBinding

class DishTypeAdapter : RecyclerView.Adapter<DishTypeAdapter.DishTypeViewHolder>() {

    inner class DishTypeViewHolder(val binding: ItemDishTypeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {

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
        holder.bind()
    }

    override fun getItemCount(): Int = 0
}
