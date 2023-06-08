package com.nguyenhl.bk.foodrecipe.feature.presentation.viewall.chef

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nguyenhl.bk.foodrecipe.core.extension.views.loadImage
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.databinding.ItemVaChefBinding
import com.nguyenhl.bk.foodrecipe.feature.dto.AuthorDto

class VAChefAdapter(
    private val onItemClick: (chef: AuthorDto) -> Unit
) : PagingDataAdapter<AuthorDto, VAChefAdapter.VAChefsHolder>(COMPARATOR) {

    inner class VAChefsHolder(val binding: ItemVaChefBinding) : ViewHolder(binding.root) {
        fun bind(chef: AuthorDto) {
            binding.apply {
                ivBackground.loadImage(chef.imageUrl)
                tvChefName.text = chef.name

                root.onClick {
                    onItemClick.invoke(chef)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: VAChefsHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VAChefsHolder {
        return VAChefsHolder(
            ItemVaChefBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<AuthorDto>() {
            override fun areItemsTheSame(oldItem: AuthorDto, newItem: AuthorDto): Boolean {
                return oldItem.idAuthor == newItem.idAuthor
            }

            override fun areContentsTheSame(oldItem: AuthorDto, newItem: AuthorDto): Boolean {
                return oldItem.idAuthor == newItem.idAuthor &&
                        oldItem.name == newItem.name
            }
        }
    }
}
