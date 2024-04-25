package com.vukasinprvulovic.application.features.cryptomarketplace

import com.vukasinprvulovic.application.features.cryptomarketplace.lifecycle.CryptoMarketplaceLifecycle
import com.vukasinprvulovic.application.features.cryptomarketplace.lifecycle.CryptoMarketplaceLifecycleManager
import com.vukasinprvulovic.application.features.cryptomarketplace.refreshing.CryptoMarketplaceRefresher
import com.vukasinprvulovic.application.features.cryptomarketplace.refreshing.CryptoMarketplaceRefreshing
import com.vukasinprvulovic.application.features.cryptomarketplace.result.CryptoMarketplaceEmitter
import com.vukasinprvulovic.application.features.cryptomarketplace.result.CryptoMarketplaceResultEmitting
import com.vukasinprvulovic.application.features.cryptomarketplace.result.CryptoMarketplaceResultSubscriber
import com.vukasinprvulovic.application.features.cryptomarketplace.result.CryptoMarketplaceResults
import com.vukasinprvulovic.application.features.cryptomarketplace.searching.CryptoMarketplaceSearching
import com.vukasinprvulovic.application.features.cryptomarketplace.searching.CryptoMarketplaceSearchingMachine
import com.vukasinprvulovic.application.features.cryptomarketplace.store.CryptoMarketplaceStore
import com.vukasinprvulovic.application.features.cryptomarketplace.store.components.SearchingToken
import com.vukasinprvulovic.configuration.di.annotations.ApplicationCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

class CryptoMarketplace @Inject constructor(
    @ApplicationCoroutineScope private val applicationCoroutineScope: CoroutineScope,
    private val cryptoMarketplaceLifecycle: CryptoMarketplaceLifecycleManager,
    private val cryptoMarketplaceEmitting: CryptoMarketplaceResultEmitting,
    private val cryptoMarketplaceStore: CryptoMarketplaceStore,
    private val cryptoMarketplaceRefresher: CryptoMarketplaceRefresher,
    private val cryptoMarketplaceSearchingMachine: CryptoMarketplaceSearchingMachine
) : CryptoMarketplaceLifecycle by cryptoMarketplaceLifecycle,
    CryptoMarketplaceEmitter by cryptoMarketplaceEmitting,
    CryptoMarketplaceRefreshing by cryptoMarketplaceRefresher,
    CryptoMarketplaceSearching by cryptoMarketplaceSearchingMachine {

    init {
        applicationCoroutineScope.launch {
            setUpCryptoMarketplaceRefresher()
            setUpCryptoMarketplaceSearchingMachine()
            cryptoMarketplaceLifecycle.initialize()
            cryptoMarketplaceLifecycle.start()
        }
    }

    private fun setUpCryptoMarketplaceRefresher() {
        cryptoMarketplaceRefresher.action = { checkingConditionsResults ->
            if (checkingConditionsResults.areMet) {
                val initialResults = CryptoMarketplaceResults(
                    cryptoMarketplaceSearchingMachine.currentSearchToken()?.let { setOf(SearchingToken(it)) } ?: emptySet()
                )
                cryptoMarketplaceStore.visitStore(initialResults).collect {
                    cryptoMarketplaceEmitting.resultsFlow.value = it
                }
            } else {
                cryptoMarketplaceEmitting.resultsFlow.value = CryptoMarketplaceResults(error = listOf(checkingConditionsResults))
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

    private fun setUpCryptoMarketplaceSearchingMachine() {
        cryptoMarketplaceSearchingMachine.searchTokenStream.onEach {
            cryptoMarketplaceRefresher.forceRefresh()
        }.launchIn(applicationCoroutineScope)
    }
}
