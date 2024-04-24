package com.vukasinprvulovic.application.features.cryptomarketplace.store.components

import com.vukasinprvulovic.application.data.sources.storage.currency.CurrencyStorage
import com.vukasinprvulovic.application.data.sources.storage.currency.filters.CryptoCurrencyStorageFilter
import com.vukasinprvulovic.application.entities.currency.UnitedStatesDollar
import com.vukasinprvulovic.application.entities.trading.data.Trading
import com.vukasinprvulovic.application.entities.trading.pair.TradingPair
import com.vukasinprvulovic.application.entities.trading.pair.TradingPairs
import com.vukasinprvulovic.application.features.cryptomarketplace.result.CryptoMarketplaceResults
import com.vukasinprvulovic.application.features.cryptomarketplace.store.CryptoMarketplaceStore
import com.vukasinprvulovic.application.features.cryptomarketplace.store.actions.CryptoMarketplaceStoreAction
import com.vukasinprvulovic.application.features.cryptomarketplace.store.actions.CryptoMarketplaceStoreActionHandler
import com.vukasinprvulovic.utils.kotlin.results.foldResultsSuspend
import kotlinx.coroutines.flow.FlowCollector
import javax.inject.Inject

internal class CryptoMarketplaceTradingPairsMaker @Inject constructor(
    private val currencyStorage: CurrencyStorage
): CryptoMarketplaceStoreActionHandler {
    override val actionIdentifier: String = CryptoMarketplaceStoreAction.MakeTradingPairs.identifier

    override suspend fun handle(context: CryptoMarketplaceStore.Context, emitter: FlowCollector<CryptoMarketplaceResults>) {
        val actionHandlingResults = foldResultsSuspend {
            context.nextAction = CryptoMarketplaceStoreAction.Finish
            val cryptoCurrencies = currencyStorage.retrieveCurrencies(CryptoCurrencyStorageFilter())
            val baseCurrencies = cryptoCurrencies.getOrThrow()
            val quoteCurrency = UnitedStatesDollar
            val tradingPairsList = baseCurrencies.map { baseCurrency ->
                TradingPair(baseCurrency, quoteCurrency, Trading.Data())
            }
            context.currentCryptoMarketplaceResults.addData(TradingPairsAreMade(TradingPairs(tradingPairsList)))
            context.nextAction = CryptoMarketplaceStoreAction.GetTradingPairsData
            Result.success(Unit)
        }
        actionHandlingResults.onFailure {
            context.currentCryptoMarketplaceResults.addError(CryptoMarketplaceResults.Error(it))
        }
    }
}

internal data class TradingPairsAreMade(
    val tradingPairs: TradingPairs
): CryptoMarketplaceResults.Data
