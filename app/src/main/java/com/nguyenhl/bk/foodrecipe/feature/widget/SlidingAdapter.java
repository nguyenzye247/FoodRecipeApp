package com.nguyenhl.bk.foodrecipe.feature.widget;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public abstract class SlidingAdapter extends FragmentStateAdapter {
    public SlidingAdapter(FragmentActivity fm) {
        super(fm);
    }

    public SlidingAdapter(Fragment fm) {
        super(fm);
    }

    abstract public CharSequence getPageTitle(int position);
}
