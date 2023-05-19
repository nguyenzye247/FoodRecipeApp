package com.nguyenhl.bk.foodrecipe.core.extension.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.distinctUntilChanged

@ObsoleteSplittiesLifecycleApi
inline fun <T> LifecycleOwner.observe(
    liveData: LiveData<T>,
    crossinline observer: (t: T?) -> Unit
): Observer<T> = Observer<T> {
    observer(it)
}.also { liveData.distinctUntilChanged().observe(this, it) }

@ObsoleteSplittiesLifecycleApi
inline fun <T : Any> LifecycleOwner.observeNotNull(
    liveData: LiveData<T>,
    crossinline observer: (t: T) -> Unit
): Observer<T> = Observer<T> {
    if (it != null) observer(it)
}.also { liveData.distinctUntilChanged().observe(this, it) }

@ObsoleteSplittiesLifecycleApi
@JvmName("observeWithLiveDataOfNullable")
inline fun <T : Any> LifecycleOwner.observeNotNull(
    liveData: LiveData<T?>,
    crossinline observer: (t: T) -> Unit
): Observer<T?> = Observer<T?> {
    if (it != null) observer(it)
}.also { liveData.distinctUntilChanged().observe(this, it) }
