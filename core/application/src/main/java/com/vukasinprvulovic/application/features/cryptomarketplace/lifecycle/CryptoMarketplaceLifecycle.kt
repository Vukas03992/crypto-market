package com.vukasinprvulovic.application.features.cryptomarketplace.lifecycle

import com.vukasinprvulovic.application.entities.currency.CryptoCurrency
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

typealias OnLifecycleEvent = suspend (state: CryptoMarketplaceLifecycle.State) -> Unit

interface CryptoMarketplaceLifecycle {
    val currentState: State

    suspend fun initialize()
    suspend fun start()
    suspend fun resume()
    suspend fun pause()
    suspend fun stop()

    fun addLifecycleListener(listener: OnLifecycleEvent)

    sealed class State(val previousState: State?) {
        data object NOTINITIALIZED : State(null)
        data class INITIALIZED(val preState: State) : State(preState)
        data class STARTED(val preState: State) : State(preState)
        data class RESUMED(val preState: State) : State(preState)
        data class PAUSED(val preState: State) : State(preState)
        data class STOPPED(val preState: State) : State(preState)
    }
}

class CryptoMarketplaceLifecycleManager @Inject constructor(): CryptoMarketplaceLifecycle {
    private val listeners: MutableList<OnLifecycleEvent> = mutableListOf()
    private val mutex: Mutex = Mutex()
    override var currentState: CryptoMarketplaceLifecycle.State = CryptoMarketplaceLifecycle.State.NOTINITIALIZED
        private set

    override suspend fun initialize() = mutex.withLock {
        if (currentState == CryptoMarketplaceLifecycle.State.NOTINITIALIZED) {
            currentState = CryptoMarketplaceLifecycle.State.INITIALIZED(currentState)
            listeners.forEach { it(currentState) }
        }
    }

    override suspend fun start() = mutex.withLock {
        if (currentState is CryptoMarketplaceLifecycle.State.INITIALIZED || currentState is CryptoMarketplaceLifecycle.State.STOPPED) {
            currentState = CryptoMarketplaceLifecycle.State.STARTED(currentState)
            listeners.forEach { it(currentState) }
        }
    }

    override suspend fun resume() = mutex.withLock {
        if (currentState is CryptoMarketplaceLifecycle.State.STARTED || currentState is CryptoMarketplaceLifecycle.State.PAUSED) {
            currentState = CryptoMarketplaceLifecycle.State.RESUMED(currentState)
            listeners.forEach { it(currentState) }
        }
    }

    override suspend fun pause() = mutex.withLock {
        if (currentState is CryptoMarketplaceLifecycle.State.RESUMED || currentState is CryptoMarketplaceLifecycle.State.STARTED) {
            currentState = CryptoMarketplaceLifecycle.State.PAUSED(currentState)
            listeners.forEach { it(currentState) }
        }
    }

    override suspend fun stop() = mutex.withLock {
        if (currentState is CryptoMarketplaceLifecycle.State.PAUSED || currentState is CryptoMarketplaceLifecycle.State.RESUMED || currentState is CryptoMarketplaceLifecycle.State.STARTED) {
            currentState = CryptoMarketplaceLifecycle.State.STOPPED(currentState)
            listeners.forEach { it(currentState) }
        }
    }

    override fun addLifecycleListener(listener: OnLifecycleEvent) {
        listeners.add(listener)
    }
}
