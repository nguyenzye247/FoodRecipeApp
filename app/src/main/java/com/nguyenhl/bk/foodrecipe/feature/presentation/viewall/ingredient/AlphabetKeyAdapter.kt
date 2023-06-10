package com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.ingredient

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.databinding.ItemAlphabetKeyBinding
import com.nguyenhl.bk.foodrecipe.feature.dto.alphabets

class AlphabetKeyAdapter(
    private val onItemClick: (alphabetKey: Char) -> Unit
) : RecyclerView.Adapter<AlphabetKeyAdapter.AlphabetKeyHolder>() {

    inner class AlphabetKeyHolder(val binding: ItemAlphabetKeyBinding) : ViewHolder(binding.root) {
        fun bind(key: Char) {
            binding.apply {
                tvAlphabetKey.text = key.toString().uppercase()
                root.onClick {
                    onItemClick(key)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlphabetKeyHolder {
        return AlphabetKeyHolder(
            ItemAlphabetKeyBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AlphabetKeyHolder, position: Int) {
        holder.bind(alphabets[position])
    }

    override fun getItemCount(): Int = alphabets.size
}