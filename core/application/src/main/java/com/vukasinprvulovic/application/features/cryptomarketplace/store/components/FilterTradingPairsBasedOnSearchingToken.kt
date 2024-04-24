package com.vukasinprvulovic.application.features.cryptomarketplace.store.components

import com.vukasinprvulovic.application.entities.trading.pair.TradingPairs
import com.vukasinprvulovic.application.features.cryptomarketplace.result.CryptoMarketplaceResults
import com.vukasinprvulovic.application.features.cryptomarketplace.result.findDataOfInstance
import com.vukasinprvulovic.application.features.cryptomarketplace.result.requireDataOfInstance
import com.vukasinprvulovic.application.features.cryptomarketplace.searching.strategy.CryptoMarketplaceSearchingStrategy
import com.vukasinprvulovic.application.features.cryptomarketplace.store.CryptoMarketplaceStore
import com.vukasinprvulovic.application.features.cryptomarketplace.store.actions.CryptoMarketplaceStoreAction
import com.vukasinprvulovic.application.features.cryptomarketplace.store.actions.CryptoMarketplaceStoreActionHandler
import com.vukasinprvulovic.utils.kotlin.results.foldResultsSuspend
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

internal class FilterTradingPairsBasedOnSearchingToken @Inject constructor(
    private val searchingStrategy: CryptoMarketplaceSearchingStrategy
) : CryptoMarketplaceStoreActionHandler {
    override val actionIdentifier: String = CryptoMarketplaceStoreAction.FilterTradingPairsBySearchToken.identifier

    override suspend fun handle(context: CryptoMarketplaceStore.Context, emitter: FlowCollector<CryptoMarketplaceResults>) {
        val actionHandlingResults = foldResultsSuspend {
            context.nextAction = CryptoMarketplaceStoreAction.Finish
            val searchingToken = context.currentCryptoMarketplaceResults.data().findDataOfInstance<SearchingToken>()
            if (searchingToken?.token == null) return@foldResultsSuspend Result.success(Unit)
            val tradingPairs = context.currentCryptoMarketplaceResults.data().requireDataOfInstance<TradingPairs>()
            val filteredTradingPairs = tradingPairs.filter { searchingStrategy.matches(searchingToken.token, it) }
            context.currentCryptoMarketplaceResults.addData(SearchedTradingPairs(TradingPairs(filteredTradingPairs)))
            emitter.emit(context.currentCryptoMarketplaceResults)
            Result.success(Unit)
        }
        actionHandlingResults.onFailure {
            context.currentCryptoMarketplaceResults.addError(CryptoMarketplaceResults.Error(it))
        }
    }
}

internal data class SearchingToken(
    val token: String?
) : CryptoMarketplaceResults.Data

data class SearchedTradingPairs(
    val results: TradingPairs
) : CryptoMarketplaceResults.Data
