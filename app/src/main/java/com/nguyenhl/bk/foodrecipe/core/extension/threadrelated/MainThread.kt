package com.nguyenhl.bk.foodrecipe.core.extension.threadrelated

import android.os.Handler
import android.os.Looper


/** This main looper cache avoids synchronization overhead when accessed repeatedly. */
@JvmField
val mainLooper: Looper = Looper.getMainLooper()!!

@JvmField
val mainThread: Thread = mainLooper.thread

val isMainThread: Boolean inline get() = mainThread === Thread.currentThread()

@PublishedApi
internal val currentThread: Any?
    inline get() = Thread.currentThread()

internal fun runOnMainThread(action: () -> Unit) {
    Handler(mainLooper).post {
        action()
    }
}

internal fun runDelayOnMainThread(delay: Long, action: () -> Unit) {
    Handler(mainLooper).postDelayed({
        action()
    }, delay)
}
