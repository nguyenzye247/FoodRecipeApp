package com.nguyenhl.bk.foodrecipe.core.extension

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

internal fun <T:Any> Observable<T>.observeOnUiThread(): Observable<T> =
    this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

internal fun <T : Any> Single<T>.observeOnUiThread(): Single<T> =
    this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

internal fun <T : Any> Observable<T>.observeOnIOThread(): Observable<T> =
    this.subscribeOn(Schedulers.io()).observeOn(Schedulers.io())

internal fun <T : Any> Single<T>.observeOnIOThread(): Single<T> =
    this.subscribeOn(Schedulers.io()).observeOn(Schedulers.io())

internal fun <T : Any> Single<T>.observeOnCompThread(): Single<T> =
    this.subscribeOn(Schedulers.computation()).observeOn(Schedulers.computation())

internal fun <T : Any> Observable<T>.observeOnCompThread(): Observable<T> =
    this.subscribeOn(Schedulers.computation()).observeOn(Schedulers.computation())
