@file:OptIn(FlowPreview::class)

package com.vukasinprvulovic.application.features.cryptomarketplace.searching

import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

interface CryptoMarketplaceSearching {
    suspend fun search(token: String)
    suspend fun clear()
}

class CryptoMarketplaceSearchingMachine @Inject constructor(): CryptoMarketplaceSearching {
    private val mutex = Mutex()
    private val searchTokenEmitter = MutableSharedFlow<String?>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.SUSPEND)
    private var searchToken: String? = null
    val searchTokenStream: Flow<String?> = searchTokenEmitter.debounce(2.seconds).onEach {
        mutex.withLock {
            searchToken = it
        }
    }

    suspend fun currentSearchToken(): String? = mutex.withLock {
        searchToken
    }

    override suspend fun search(token: String) {
        searchTokenEmitter.emit(token)
    }

    override suspend fun clear() {
        searchTokenEmitter.emit(null)
    }
}
