package com.nguyenhl.bk.foodrecipe.core.extension

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Runs the block of code in a coroutine when the lifecycle is at least STARTED.
 * the coroutine will be cancelled when the ON_STOP event happens and will
 * restart executing if the lifecycle receives the ON_START event again.
 */
internal fun LifecycleCoroutineScope.launchRepeatOnStarted(
    lifecycleOwner: LifecycleOwner,
    block: suspend CoroutineScope.() -> Unit
) {
    this.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            block()
        }
    }
}

/**
 * Runs the block of code in a coroutine when the lifecycle is at least RESUME.
 * the coroutine will be cancelled when the ON_PAUSE event happens and will
 * restart executing if the lifecycle receives the ON_RESUME event again.
 */
internal fun LifecycleCoroutineScope.launchRepeatOnResume(
    lifecycleOwner: LifecycleOwner,
    block: suspend CoroutineScope.() -> Unit
) {
    this.launch {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            block()
        }
    }
}
