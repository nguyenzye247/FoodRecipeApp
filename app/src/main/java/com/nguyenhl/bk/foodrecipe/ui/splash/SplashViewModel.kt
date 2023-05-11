package com.nguyenhl.bk.foodrecipe.ui.splash

import com.nguyenhl.bk.foodrecipe.base.BaseInput
import com.nguyenhl.bk.foodrecipe.base.BaseViewModel
import com.nguyenhl.bk.foodrecipe.extension.observeOnUiThread
import io.reactivex.rxjava3.core.Observable
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.concurrent.TimeUnit

class SplashViewModel : BaseViewModel(BaseInput.NoInput) {
    private val timeFinish: Long = 5000
    val isFinish = MutableStateFlow(false)

    init {
        addDisposables(
            Observable.timer(timeFinish, TimeUnit.MILLISECONDS)
                .observeOnUiThread()
                .subscribe {
                    isFinish.value = true
                })
    }
}
