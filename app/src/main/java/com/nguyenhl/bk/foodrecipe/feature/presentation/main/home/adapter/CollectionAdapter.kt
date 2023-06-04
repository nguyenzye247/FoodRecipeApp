package com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nguyenhl.bk.foodrecipe.core.extension.views.loadImage
import com.nguyenhl.bk.foodrecipe.databinding.ItemCollectionBinding
import com.nguyenhl.bk.foodrecipe.databinding.ItemDishTypeBinding
import com.nguyenhl.bk.foodrecipe.feature.dto.CollectionDto

class CollectionAdapter(
    private val collections: List<CollectionDto>
) : RecyclerView.Adapter<CollectionAdapter.CollectionViewHolder>() {

    inner class CollectionViewHolder(val binding: ItemCollectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(collection: CollectionDto) {
            binding.apply {
                ivBackground.loadImage(collection.imageUrl)
                tvCollectionName.text = collection.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        return CollectionViewHolder(
            ItemCollectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        holder.bind(collections[position])
    }

    override fun getItemCount(): Int = collections.size
}
