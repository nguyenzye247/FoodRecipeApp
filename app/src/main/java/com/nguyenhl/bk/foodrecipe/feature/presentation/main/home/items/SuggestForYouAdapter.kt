package com.nguyenhl.bk.foodrecipe.feature.presentation.main.home.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nguyenhl.bk.foodrecipe.databinding.ItemSuggestForYouBinding

class SuggestForYouAdapter : RecyclerView.Adapter<SuggestForYouAdapter.SuggestForYouViewHolder>() {


    inner class SuggestForYouViewHolder(val binding: ItemSuggestForYouBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestForYouViewHolder {
        return SuggestForYouViewHolder(
            ItemSuggestForYouBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SuggestForYouViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount(): Int = 0
}
