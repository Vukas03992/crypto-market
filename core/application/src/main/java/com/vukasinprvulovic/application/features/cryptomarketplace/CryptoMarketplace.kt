package com.vukasinprvulovic.application.features.cryptomarketplace

import com.vukasinprvulovic.application.features.cryptomarketplace.lifecycle.CryptoMarketplaceLifecycle
import com.vukasinprvulovic.application.features.cryptomarketplace.lifecycle.CryptoMarketplaceLifecycleManager
import com.vukasinprvulovic.application.features.cryptomarketplace.result.CryptoMarketplaceEmitter
import com.vukasinprvulovic.application.features.cryptomarketplace.result.CryptoMarketplaceResultEmitting
import com.vukasinprvulovic.configuration.di.annotations.ApplicationCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class CryptoMarketplace @Inject constructor(
    @ApplicationCoroutineScope private val applicationCoroutineScope: CoroutineScope,
    private val cryptoMarketplaceLifecycle: CryptoMarketplaceLifecycleManager,
    private val cryptoMarketplaceEmitting: CryptoMarketplaceResultEmitting
): CryptoMarketplaceLifecycle by cryptoMarketplaceLifecycle,
    CryptoMarketplaceEmitter by cryptoMarketplaceEmitting {

    init {
        applicationCoroutineScope.launch {
            cryptoMarketplaceLifecycle.initialize()
            cryptoMarketplaceLifecycle.start()
            cryptoMarketplaceLifecycle.resume()
        }
    }
}
