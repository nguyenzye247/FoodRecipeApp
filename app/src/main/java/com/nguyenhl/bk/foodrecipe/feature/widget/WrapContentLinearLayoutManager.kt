package com.nguyenhl.bk.foodrecipe.feature.widget

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber

class WrapContentLinearLayoutManager(
    context: Context,
    @RecyclerView.Orientation orientation: Int,
    reverseLayout: Boolean
): LinearLayoutManager(context, orientation, reverseLayout) {
    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: IndexOutOfBoundsException) {
            Timber.e(e)
        }
    }
}
