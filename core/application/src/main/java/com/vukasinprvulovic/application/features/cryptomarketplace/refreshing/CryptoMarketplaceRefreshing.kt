package com.vukasinprvulovic.application.features.cryptomarketplace.refreshing

import com.vukasinprvulovic.configuration.di.annotations.ApplicationCoroutineDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

interface CryptoMarketplaceRefreshing {

    fun configure(config: Configuration.() -> Unit)

    data class Configuration(
        var interval: Duration = 5.seconds
    )
}

class CryptoMarketplaceRefresher @Inject constructor(
    @ApplicationCoroutineDispatcher private val coroutineDispatcher: CoroutineDispatcher
): CryptoMarketplaceRefreshing {
    private val refreshingCoroutineScope = CoroutineScope(SupervisorJob() + coroutineDispatcher)
    private val refreshingJobMutex = Mutex()
    private var currentRefreshingJob: Job? = null
    private val configuration = CryptoMarketplaceRefreshing.Configuration()
    lateinit var action: suspend () -> Unit

    fun start() {
        refreshingCoroutineScope.launch {
            refreshingJobMutex.withLock {
                currentRefreshingJob?.cancel()
                currentRefreshingJob = refreshingCoroutineScope.launch {
                    while(true) {
                        ensureActive()
                        action()
                        delay(configuration.interval)
                    }
                }
            }
        }
    }

    fun stop() {
        refreshingCoroutineScope.launch {
            refreshingJobMutex.withLock {
                currentRefreshingJob?.cancel()
                currentRefreshingJob = null
            }
        }
    }

    fun shutDown() {
        refreshingCoroutineScope.cancel()
        currentRefreshingJob = null
    }

    override fun configure(config: CryptoMarketplaceRefreshing.Configuration.() -> Unit) {
        configuration.config()
    }
}
