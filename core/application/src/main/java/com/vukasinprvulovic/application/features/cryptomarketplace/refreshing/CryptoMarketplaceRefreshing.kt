package com.vukasinprvulovic.application.features.cryptomarketplace.refreshing

import androidx.annotation.VisibleForTesting
import com.vukasinprvulovic.application.features.cryptomarketplace.refreshing.conditions.CryptoMarketplaceRefreshingConditions
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
    @ApplicationCoroutineDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val cryptoMarketplaceRefreshingConditions: CryptoMarketplaceRefreshingConditions
): CryptoMarketplaceRefreshing {
    private val refreshingCoroutineScope = CoroutineScope(SupervisorJob() + coroutineDispatcher)
    private val refreshingJobMutex = Mutex()
    private var currentRefreshingJob: Job? = null
    private val configuration = CryptoMarketplaceRefreshing.Configuration()
    var action: suspend (CryptoMarketplaceRefreshingConditions.Results) -> Unit = { }
    @VisibleForTesting
    var internalAction: suspend (CryptoMarketplaceRefreshingConditions.Results) -> Boolean = {
        action(it); false
    }

    init {
        cryptoMarketplaceRefreshingConditions.subscribeToConditionChanges {
            start()
        }
    }

    fun start() {
        refreshingCoroutineScope.launch {
            refreshingJobMutex.withLock {
                startRefreshing()
            }
        }
    }

    fun stop() {
        refreshingCoroutineScope.launch {
            refreshingJobMutex.withLock {
                stopRefreshing()
            }
        }
    }

    fun shutDown() {
        refreshingCoroutineScope.cancel()
        currentRefreshingJob = null
    }

    fun forceRefresh() {
        refreshingCoroutineScope.launch {
            refreshingJobMutex.withLock {
                stopRefreshing()
                startRefreshing()
            }
        }
    }

    private suspend fun startRefreshing() {
        currentRefreshingJob?.cancel()
        currentRefreshingJob = refreshingCoroutineScope.launch {
            val conditionsResults = cryptoMarketplaceRefreshingConditions.areConditionsMet()
            val areConditionsMet = conditionsResults.conditions.isEmpty()
            if (areConditionsMet) {
                var stop = false
                while(!stop) {
                    ensureActive()
                    stop = internalAction(cryptoMarketplaceRefreshingConditions.areConditionsMet())
                    delay(configuration.interval)
                }
            } else {
                action(conditionsResults)
            }
        }
    }

    private fun stopRefreshing() {
        currentRefreshingJob?.cancel()
        currentRefreshingJob = null
    }

    override fun configure(config: CryptoMarketplaceRefreshing.Configuration.() -> Unit) {
        configuration.config()
    }

    @VisibleForTesting
    fun getCurrentJob() = currentRefreshingJob
}
