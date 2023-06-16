package com.nguyenhl.bk.foodrecipe.feature.data.datasource.api

import com.skydoves.sandwich.retry.RetryPolicy

class GlobalRetryPolicy: RetryPolicy {
    override fun retryTimeout(attempt: Int, message: String?): Int {
        return 3000
    }

    override fun shouldRetry(attempt: Int, message: String?): Boolean {
        return attempt <= 3
    }
}
