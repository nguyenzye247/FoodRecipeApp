package com.nguyenhl.bk.foodrecipe.feature.helper

import com.nguyenhl.bk.foodrecipe.core.extension.observeOnIOThread
import com.nguyenhl.bk.foodrecipe.core.extension.observeOnUiThread
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject

object
RxBus {
    private val publisher = PublishSubject.create<Any>()

    fun publish(event: Any) {
        publisher.onNext(event)
    }

    // Listen should return an Observable and not the publisher
    // Using ofType we filter only events that match that class type
    fun <T> listen(eventType: Class<T>): Observable<T> = publisher.ofType(eventType)
}

internal inline fun <reified T : Any>listenRxEventOnUI(crossinline action: (event: T) -> Unit): Disposable {
    return RxBus.listen(T::class.java)
        .observeOnUiThread()
        .subscribe {
            action(it)
        }
}

internal inline fun <reified T: Any>listenRxEventOnIO(crossinline action: (event: T) -> Unit): Disposable {
    return RxBus.listen(T::class.java)
        .observeOnIOThread()
        .subscribe {
            action(it)
        }
}

class RxEvent {
    class EventUnauthorized()
    class EventValidUserInfo()
    class EventApplySearchFilter()
    class EventLikedRecipe()
}
