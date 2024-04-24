package com.vukasinprvulovic.application.features.cryptomarketplace.store.actions

import com.vukasinprvulovic.application.features.cryptomarketplace.result.CryptoMarketplaceResults
import com.vukasinprvulovic.application.features.cryptomarketplace.store.CryptoMarketplaceStore
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

sealed interface CryptoMarketplaceStoreAction {
    val identifier: String
    data object Open: CryptoMarketplaceStoreAction {
        override val identifier: String = "Open"
    }
    data object Close: CryptoMarketplaceStoreAction {
        override val identifier: String = "Close"
    }
}

internal interface CryptoMarketplaceStoreActionHandler {
    val actionIdentifier: String
    suspend fun handle(context: CryptoMarketplaceStore.Context, emitter: FlowCollector<CryptoMarketplaceResults>)
}

internal class OpenActionHandler @Inject constructor(): CryptoMarketplaceStoreActionHandler {
    override val actionIdentifier: String = CryptoMarketplaceStoreAction.Open.identifier

    override suspend fun handle(context: CryptoMarketplaceStore.Context, emitter: FlowCollector<CryptoMarketplaceResults>) {
        emitter.emit(context.currentCryptoMarketplaceResults)
        context.nextAction = CryptoMarketplaceStoreAction.Close
    }
}
