package com.nguyenhl.bk.foodrecipe.feature.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import org.koin.core.KoinComponent

open class BaseViewModel(val inputData: BaseInput) : ViewModel(), KoinComponent {
    protected val subscription: CompositeDisposable = CompositeDisposable()
    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    fun liveIsLoading(): LiveData<Boolean> = _isLoading
    fun setLoading(isLoading: Boolean) {
        _isLoading.postValue(isLoading)
    }

    protected fun addDisposables(vararg ds: Disposable) {
        ds.forEach { subscription.add(it) }
    }

    override fun onCleared() {
        super.onCleared()
        subscription.clear()
    }
}
