package com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.collection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nguyenhl.bk.foodrecipe.core.extension.views.loadImage
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.databinding.ItemVaCollectionBinding
import com.nguyenhl.bk.foodrecipe.feature.dto.CollectionDto

class VACollectionAdapter(
    private val collections: List<CollectionDto>,
    private val onItemClick: (collection: CollectionDto) -> Unit
) : RecyclerView.Adapter<VACollectionAdapter.VACollectionHolder>() {

    inner class VACollectionHolder(val binding: ItemVaCollectionBinding) :
        ViewHolder(binding.root) {
        fun bind(collection: CollectionDto) {
            binding.apply {
                ivBackground.loadImage(collection.imageUrl)
                tvCollectionName.apply {
                    text = collection.name
                }
                root.onClick {
                    onItemClick.invoke(collection)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: VACollectionHolder, position: Int) {
        holder.bind(collections[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VACollectionHolder {
        return VACollectionHolder(
            ItemVaCollectionBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = collections.size
}
