package com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nguyenhl.bk.foodrecipe.core.extension.views.loadImage
import com.nguyenhl.bk.foodrecipe.databinding.ItemChefBinding
import com.nguyenhl.bk.foodrecipe.feature.dto.AuthorDto

class TopChefAdapter(
    private val topChefs: List<AuthorDto>
) : RecyclerView.Adapter<TopChefAdapter.TopChefViewHolder>() {

    inner class TopChefViewHolder(val binding: ItemChefBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(chef: AuthorDto) {
            binding.apply {
                ivBackground.loadImage(chef.imageUrl)
                tvChefName.text = chef.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopChefViewHolder {
        return TopChefViewHolder(
            ItemChefBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: TopChefViewHolder, position: Int) {
        holder.bind(topChefs[position])
    }

    override fun getItemCount(): Int = topChefs.size
}
