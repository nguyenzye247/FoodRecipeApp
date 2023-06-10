package com.nguyenhl.bk.foodrecipe.feature.presentation.viewall

import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.nguyenhl.bk.foodrecipe.databinding.ActivityViewAllBinding
import com.nguyenhl.bk.foodrecipe.feature.base.BaseActivity

abstract class BaseViewAllActivity<V : BaseViewAllViewModel> :
    BaseActivity<ActivityViewAllBinding, V>() {

    override fun getLazyBinding() = lazy { ActivityViewAllBinding.inflate(layoutInflater) }

    abstract fun getContentType(): ViewAllContentType
    abstract fun getRecyclerviewLayoutManager(): LayoutManager
}
