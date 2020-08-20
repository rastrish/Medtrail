package com.zerone.medtrail.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build


class NetworkHelper(private val context: Context) {

    companion object {
        const val TAG = "NetworkHelper"
    }


    fun isNetworkConnected(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            val activeNetwork = cm.activeNetworkInfo
            return activeNetwork?.isConnected ?: false
        } else {
            val network = cm.allNetworks
            var hasNetwork = false;
            if (network.isNotEmpty()) {
                for (network in network) {
                    val networkCapabilities = cm.getNetworkCapabilities(network)
                    if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                        hasNetwork = true
                    }
                }

            }
            return hasNetwork
        }
    }
}