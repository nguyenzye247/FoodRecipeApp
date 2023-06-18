package com.nguyenhl.bk.foodrecipe.feature.presentation.search.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nguyenhl.bk.foodrecipe.core.extension.views.onClick
import com.nguyenhl.bk.foodrecipe.databinding.ItemSearchFilterBinding
import com.nguyenhl.bk.foodrecipe.feature.dto.searchfilter.SearchFilterItemDto

class SearchFilterItemAdapter(
    private val filterItems: List<SearchFilterItemDto>,
    private val onItemClick: (filterItem: SearchFilterItemDto) -> Unit
): RecyclerView.Adapter<SearchFilterItemAdapter.SearchFilterItemHolder>(){

    inner class SearchFilterItemHolder(val binding: ItemSearchFilterBinding): ViewHolder(binding.root) {
        fun bind(searchFilterItem: SearchFilterItemDto) {
            binding.apply {
                tvFilterItem.text = searchFilterItem.name
                tvFilterItem.isSelected = searchFilterItem.isSelected

                root.onClick {
                    tvFilterItem.isSelected = !searchFilterItem.isSelected
                    searchFilterItem.isSelected = !searchFilterItem.isSelected
                    onItemClick(searchFilterItem)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchFilterItemHolder {
        return SearchFilterItemHolder(
            ItemSearchFilterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SearchFilterItemHolder, position: Int) {
        holder.bind(filterItems[position])
    }

    override fun getItemCount(): Int = filterItems.size

}
