package com.vukasinprvulovic.application.features.cryptomarketplace.store.components

import com.vukasinprvulovic.application.data.sources.remote.trading.pairs.TradingPairsRemoteSource
import com.vukasinprvulovic.application.features.cryptomarketplace.result.CryptoMarketplaceResults
import com.vukasinprvulovic.application.features.cryptomarketplace.result.findDataOfInstance
import com.vukasinprvulovic.application.features.cryptomarketplace.result.requireDataOfInstance
import com.vukasinprvulovic.application.features.cryptomarketplace.store.CryptoMarketplaceStore
import com.vukasinprvulovic.application.features.cryptomarketplace.store.actions.CryptoMarketplaceStoreAction
import com.vukasinprvulovic.application.features.cryptomarketplace.store.actions.CryptoMarketplaceStoreActionHandler
import com.vukasinprvulovic.utils.kotlin.results.foldResultsSuspend
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

internal class GetTradingPairsData @Inject constructor(
    private val tradingPairsRemoteSource: TradingPairsRemoteSource
): CryptoMarketplaceStoreActionHandler {
    override val actionIdentifier: String = CryptoMarketplaceStoreAction.GetTradingPairsData.identifier

    override suspend fun handle(context: CryptoMarketplaceStore.Context, emitter: FlowCollector<CryptoMarketplaceResults>) {
        val actionHandlingResults = foldResultsSuspend {
            context.nextAction = CryptoMarketplaceStoreAction.FilterTradingPairsBySearchToken
            val tradingPairsWithoutData = context.currentCryptoMarketplaceResults.data().requireDataOfInstance<TradingPairsAreMade>()
            val fetchingDataResults = tradingPairsRemoteSource.fetchTradingPairsData(tradingPairsWithoutData.tradingPairs)
            val tradingPairsWithData = fetchingDataResults.getOrThrow()
            context.currentCryptoMarketplaceResults.addData(tradingPairsWithData)
            if (shouldEmitResults(context)) emitter.emit(context.currentCryptoMarketplaceResults)
            Result.success(Unit)
        }
        actionHandlingResults.onFailure {
            context.currentCryptoMarketplaceResults.addError(CryptoMarketplaceResults.Error(it))
        }
    }

    private suspend fun shouldEmitResults(context: CryptoMarketplaceStore.Context): Boolean {
        return context.currentCryptoMarketplaceResults.data().findDataOfInstance<SearchingToken>()?.token == null
    }
}
