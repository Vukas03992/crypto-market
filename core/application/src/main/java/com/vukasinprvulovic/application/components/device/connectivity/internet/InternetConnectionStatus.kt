package com.vukasinprvulovic.application.components.device.connectivity.internet

interface InternetConnectionStatus {
    val isConnected: Boolean
    val status: InternetConnectionEvent.Status
    fun subscribeToInternetConnectionEvents(subscriber: InternetConnectionEvent.Subscriber)
}

sealed interface InternetConnectionEvent {

    data class StatusChange(
        val previousStatus: Status?,
        val newStatus: Status
    ): InternetConnectionEvent {
        val isConnected: Boolean
            get() = newStatus.isConnected

        val isConnectionLost: Boolean
            get() = previousStatus?.isConnected == true && !newStatus.isConnected
    }

    sealed class Status: InternetConnectionEvent {
        abstract val timestamp: Long
        data class Connected(
            val networkIdentifier: Long,
            val isInternetAvailable: Boolean,
            override val timestamp: Long
        ): Status()
        data class Disconnected(
            val networkIdentifier: Long,
            override val timestamp: Long
        ): Status()

        val isConnected: Boolean
            get() = this is Connected
    }

    fun interface Subscriber {
        fun onInternetConnectionEvent(event: InternetConnectionEvent)
    }
}
