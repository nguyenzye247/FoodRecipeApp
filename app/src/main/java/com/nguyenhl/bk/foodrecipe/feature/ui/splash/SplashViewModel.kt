package com.nguyenhl.bk.foodrecipe.feature.ui.splash

import com.nguyenhl.bk.foodrecipe.core.extension.observeOnUiThread
import com.nguyenhl.bk.foodrecipe.feature.base.BaseInput
import com.nguyenhl.bk.foodrecipe.feature.base.BaseViewModel
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
