package com.godzistudio.godzimind.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

/** Comprobación rápida para no esperar un timeout cuando no hay internet. */
object NetworkStatus {

    fun isOnline(context: Context): Boolean {
        val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
            ?: return true
        val network = manager.activeNetwork ?: return false
        val capabilities = manager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
