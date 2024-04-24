package com.vukasinprvulovic.application.features.cryptomarketplace.result

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

typealias CryptoMarketplaceResultListener = suspend (results: CryptoMarketplaceResults) -> Unit
interface CryptoMarketplaceResultSubscriber {
    val currentResults: CryptoMarketplaceResults
    fun subscribe(scope: CoroutineScope, listener: CryptoMarketplaceResultListener)
}

internal interface CryptoMarketplaceEmitter: CryptoMarketplaceResultSubscriber {
    val resultsFlow: MutableStateFlow<CryptoMarketplaceResults>
}

class CryptoMarketplaceResultEmitting @Inject constructor(): CryptoMarketplaceEmitter {
    override val resultsFlow: MutableStateFlow<CryptoMarketplaceResults> = MutableStateFlow(CryptoMarketplaceResults(emptyList(), emptyList()))

    override val currentResults: CryptoMarketplaceResults
        get() = resultsFlow.value

    override fun subscribe(scope: CoroutineScope, listener: CryptoMarketplaceResultListener) {
        scope.launch {
            resultsFlow.collect {
                listener(it)
            }
        }
    }
}
