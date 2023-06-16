package com.nguyenhl.bk.foodrecipe.feature.presentation.cooking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.core.extension.views.setTextIncludeDigit
import com.nguyenhl.bk.foodrecipe.databinding.ItemCookingDirectionBinding
import com.nguyenhl.bk.foodrecipe.feature.dto.view.DirectionItem

class CookingItemAdapter(
    private val directions: List<DirectionItem>
): RecyclerView.Adapter<CookingItemAdapter.CookingItemHolder>() {

    inner class CookingItemHolder(val binding: ItemCookingDirectionBinding): ViewHolder(binding.root) {
        fun bind(direction: DirectionItem) {
            binding.apply {
                tvCookingDirection.setTextIncludeDigit(direction.direction)
                cbSelectDirection.isChecked = direction.isSelected

                cbSelectDirection.setOnCheckedChangeListener { _, isChecked ->
                    direction.isSelected = isChecked
                }
                tvCookingDirection.onClick {
                    cbSelectDirection.isSelected = !cbSelectDirection.isSelected
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CookingItemHolder {
        return CookingItemHolder(
            ItemCookingDirectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CookingItemHolder, position: Int) {
        holder.bind(directions[position])
    }

    override fun getItemCount(): Int = directions.size
}