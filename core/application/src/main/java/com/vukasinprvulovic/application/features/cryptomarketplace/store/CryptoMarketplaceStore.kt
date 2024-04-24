package com.vukasinprvulovic.application.features.cryptomarketplace.store

import com.vukasinprvulovic.application.features.cryptomarketplace.result.CryptoMarketplaceResults
import com.vukasinprvulovic.application.features.cryptomarketplace.store.actions.CryptoMarketplaceStoreAction
import com.vukasinprvulovic.application.features.cryptomarketplace.store.actions.CryptoMarketplaceStoreActionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface CryptoMarketplaceStore {
    fun visitStore(initialCryptoMarketplaceResults: CryptoMarketplaceResults, initialAction: CryptoMarketplaceStoreAction = CryptoMarketplaceStoreAction.Start): Flow<CryptoMarketplaceResults>

    data class Context(
        var nextAction: CryptoMarketplaceStoreAction,
        var currentCryptoMarketplaceResults: CryptoMarketplaceResults
    )
}

internal class InternalCryptoMarketplaceStore @Inject constructor(
    private val actionHandlers: Set<CryptoMarketplaceStoreActionHandler>
): CryptoMarketplaceStore {

    override fun visitStore(initialCryptoMarketplaceResults: CryptoMarketplaceResults, initialAction: CryptoMarketplaceStoreAction): Flow<CryptoMarketplaceResults> = flow {
        val context = CryptoMarketplaceStore.Context(initialAction, initialCryptoMarketplaceResults)
        while(context.nextAction !is CryptoMarketplaceStoreAction.Finish) {
            val actionHandler = actionHandlers.find { it.actionIdentifier == context.nextAction.identifier } ?: error("No action handler found for ${context.nextAction.identifier}")
            actionHandler.handle(context, this)
        }
    }
}
