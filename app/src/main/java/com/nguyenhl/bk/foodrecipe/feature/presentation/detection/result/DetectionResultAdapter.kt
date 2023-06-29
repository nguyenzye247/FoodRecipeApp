package com.nguyenhl.bk.foodrecipe.feature.presentation.detection.result

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.nguyenhl.bk.foodrecipe.databinding.ItemDetectionResultBinding

class DetectionResultAdapter(): RecyclerView.Adapter<DetectionResultAdapter.DetectionResultItemHolder>() {

    inner class DetectionResultItemHolder(val binding: ItemDetectionResultBinding): ViewHolder(binding.root) {

        fun bind() {

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetectionResultItemHolder {
        return DetectionResultItemHolder(
            ItemDetectionResultBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: DetectionResultItemHolder, position: Int) {
        holder.bind()
    }
}
