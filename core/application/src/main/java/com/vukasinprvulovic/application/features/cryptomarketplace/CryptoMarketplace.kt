package com.vukasinprvulovic.application.features.cryptomarketplace

import com.vukasinprvulovic.application.features.cryptomarketplace.lifecycle.CryptoMarketplaceLifecycle
import com.vukasinprvulovic.application.features.cryptomarketplace.lifecycle.CryptoMarketplaceLifecycleManager
import com.vukasinprvulovic.application.features.cryptomarketplace.refreshing.CryptoMarketplaceRefresher
import com.vukasinprvulovic.application.features.cryptomarketplace.refreshing.CryptoMarketplaceRefreshing
import com.vukasinprvulovic.application.features.cryptomarketplace.result.CryptoMarketplaceEmitter
import com.vukasinprvulovic.application.features.cryptomarketplace.result.CryptoMarketplaceResultEmitting
import com.vukasinprvulovic.application.features.cryptomarketplace.result.CryptoMarketplaceResultSubscriber
import com.vukasinprvulovic.application.features.cryptomarketplace.result.CryptoMarketplaceResults
import com.vukasinprvulovic.application.features.cryptomarketplace.store.CryptoMarketplaceStore
import com.vukasinprvulovic.configuration.di.annotations.ApplicationCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class CryptoMarketplace @Inject constructor(
    @ApplicationCoroutineScope private val applicationCoroutineScope: CoroutineScope,
    private val cryptoMarketplaceLifecycle: CryptoMarketplaceLifecycleManager,
    private val cryptoMarketplaceEmitting: CryptoMarketplaceResultEmitting,
    private val cryptoMarketplaceStore: CryptoMarketplaceStore,
    private val cryptoMarketplaceRefresher: CryptoMarketplaceRefresher
) : CryptoMarketplaceLifecycle by cryptoMarketplaceLifecycle,
    CryptoMarketplaceEmitter by cryptoMarketplaceEmitting,
    CryptoMarketplaceRefreshing by cryptoMarketplaceRefresher {

    init {
        applicationCoroutineScope.launch {
            setUpCryptoMarketplaceRefresher()
            cryptoMarketplaceLifecycle.initialize()
            cryptoMarketplaceLifecycle.start()
        }
    }

    private fun setUpCryptoMarketplaceRefresher() {
        cryptoMarketplaceRefresher.action = {
            cryptoMarketplaceStore.visitStore(CryptoMarketplaceResults()).collect {
                cryptoMarketplaceEmitting.resultsFlow.value = it
            }
        }
        addLifecycleListener {
            when {
                it is CryptoMarketplaceLifecycle.State.RESUMED -> cryptoMarketplaceRefresher.start()
                it is CryptoMarketplaceLifecycle.State.PAUSED -> cryptoMarketplaceRefresher.stop()
                it is CryptoMarketplaceLifecycle.State.STOPPED -> cryptoMarketplaceRefresher.shutDown()
            }
        }
    }
}
