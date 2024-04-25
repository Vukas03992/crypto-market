package com.vukasinprvulovic.device.internet

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.vukasinprvulovic.application.components.device.connectivity.internet.InternetConnectionEvent
import com.vukasinprvulovic.application.components.device.connectivity.internet.InternetConnectionStatus
import com.vukasinprvulovic.configuration.di.annotations.ApplicationCoroutineScope
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import java.util.concurrent.atomic.AtomicReference
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

@Singleton
class InternetConnection @Inject constructor(
    @ApplicationContext private val context: Context,
    @ApplicationCoroutineScope private val coroutineScope: CoroutineScope,
) : InternetConnectionStatus {
    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private val connectivityNetworkCallback: ConnectivityManager.NetworkCallback

    private val subscribers = mutableSetOf<InternetConnectionEvent.Subscriber>()
    private val internetConnectionStatusReference = AtomicReference<InternetConnectionEvent.Status>()

    override val isConnected: Boolean
        get() {
            val connectionStatus = getInternetConnectionStatusSync()
            return connectionStatus.isConnected
        }

    override val status: InternetConnectionEvent.Status
        get() = getInternetConnectionStatusSync()

    override fun subscribeToInternetConnectionEvents(subscriber: InternetConnectionEvent.Subscriber) {
        subscribers.add(subscriber)
    }

    init {
        internetConnectionStatusReference.set(InternetConnectionEvent.Status.Disconnected(
            networkIdentifier = -1L,
            timestamp = Clock.System.now().toEpochMilliseconds())
        )
        connectivityNetworkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                val statusChange = getConnectionStatusChange(network, connectivityManager.getNetworkCapabilities(network))
                emitStatusChange(statusChange)
            }
            override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
                val statusChange = getConnectionStatusChange(network, networkCapabilities)
                emitStatusChange(statusChange)
            }
            override fun onLost(network: Network) {
                val statusChange = getConnectionStatusChange(network, null)
                val previousConnectedState = (statusChange.previousStatus as? InternetConnectionEvent.Status.Connected) ?: return
                if (previousConnectedState.networkIdentifier == network.networkHandle) {
                    coroutineScope.launch {
                        delay(3.seconds)
                        val currentState = getInternetConnectionStatusSync()
                        if (currentState is InternetConnectionEvent.Status.Disconnected) {
                            emitStatusChange(statusChange)
                        }
                    }
                }
            }
        }
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, connectivityNetworkCallback)
    }

    private fun getConnectionStatusChange(network: Network, networkCapabilities: NetworkCapabilities?): InternetConnectionEvent.StatusChange {
        val previousStatus = internetConnectionStatusReference.get()
        val newState = when {
            networkCapabilities == null -> InternetConnectionEvent.Status.Disconnected(
                -1L,
                timestamp = Clock.System.now().toEpochMilliseconds()
            )
            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) -> {
                val isInternetAvailable = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                InternetConnectionEvent.Status.Connected(
                    networkIdentifier = network.networkHandle,
                    isInternetAvailable = isInternetAvailable,
                    timestamp = Clock.System.now().toEpochMilliseconds()
                )
            }
            else -> InternetConnectionEvent.Status.Disconnected(
                networkIdentifier = network.networkHandle,
                timestamp = Clock.System.now().toEpochMilliseconds()
            )
        }
        internetConnectionStatusReference.set(newState)
        return InternetConnectionEvent.StatusChange(previousStatus, newState)
    }

    private fun emitStatusChange(statusChange: InternetConnectionEvent.StatusChange) {
        subscribers.forEach { it.onInternetConnectionEvent(statusChange) }
    }

    private fun getInternetConnectionStatusSync(): InternetConnectionEvent.Status {
        val network = connectivityManager.activeNetwork ?: return run {
            val currentCachedStatus = internetConnectionStatusReference.get()
            InternetConnectionEvent.Status.Disconnected(
                networkIdentifier = (currentCachedStatus as? InternetConnectionEvent.Status.Connected)?.networkIdentifier ?: -1L,
                timestamp = Clock.System.now().toEpochMilliseconds()
            )
        }
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return getConnectionStatusChange(network, capabilities).newStatus
    }
}
