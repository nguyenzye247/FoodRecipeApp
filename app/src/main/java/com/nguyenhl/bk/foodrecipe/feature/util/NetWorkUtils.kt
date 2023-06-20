package com.nguyenhl.bk.foodrecipe.feature.util

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

object NetworkUtils {

    fun isNetworkAvailable(connectivityManager: ConnectivityManager): Boolean {
        var isConnected = false
        val network = connectivityManager.activeNetwork
        network?.let { connectivityManager.getNetworkCapabilities(it) }
            ?.let { isConnected = it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) }
            ?: kotlin.run { isConnected = false }
        return isConnected
    }

    fun isWifiAvailable(connectivityManager: ConnectivityManager): Boolean {
        var isConnected = false
        val network = connectivityManager.activeNetwork
        network?.let { connectivityManager.getNetworkCapabilities(it) }
            ?.takeIf { it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) }
            ?.let { isConnected = it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) }
            ?: kotlin.run { isConnected = false }
        return isConnected
    }
}
