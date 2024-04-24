package com.vukasinprvulovic.application.feature.cryptomarketplace.store.intergration

import com.vukasinprvulovic.application.data.sources.remote.trading.pairs.TradingPairsRemoteSource
import com.vukasinprvulovic.application.data.sources.storage.currency.CurrencyStorage
import com.vukasinprvulovic.application.entities.currency.CryptoCurrency
import com.vukasinprvulovic.application.entities.currency.Currency
import com.vukasinprvulovic.application.entities.currency.UnitedStatesDollar
import com.vukasinprvulovic.application.entities.currency.types.crypto.properties.CryptoProperties
import com.vukasinprvulovic.application.entities.ticker.Ticker
import com.vukasinprvulovic.application.entities.trading.data.Trading
import com.vukasinprvulovic.application.entities.trading.data.price.bid.BID
import com.vukasinprvulovic.application.entities.trading.pair.TradingPair
import com.vukasinprvulovic.application.entities.trading.pair.TradingPairs
import com.vukasinprvulovic.application.features.cryptomarketplace.result.CryptoMarketplaceResults
import com.vukasinprvulovic.application.features.cryptomarketplace.searching.strategy.DefaultCryptoMarketplaceSearchingStrategy
import com.vukasinprvulovic.application.features.cryptomarketplace.store.InternalCryptoMarketplaceStore
import com.vukasinprvulovic.application.features.cryptomarketplace.store.actions.StartActionHandler
import com.vukasinprvulovic.application.features.cryptomarketplace.store.components.FilterTradingPairsBasedOnSearchingToken
import com.vukasinprvulovic.application.features.cryptomarketplace.store.components.GetTradingPairsData
import com.vukasinprvulovic.application.features.cryptomarketplace.store.components.TradingPairsMaker
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CryptoMarketplaceStoreIntegrationTests {

    @Test
    fun `when all action handlers are giving expected results then store emits expected results to CryptoMarketplace`() = runTest {
        val tradingPair = TradingPair(CryptoCurrency(CryptoProperties("BTC", "Bitcoin", "Bitcoin", "BTC", Ticker("BTC"))), UnitedStatesDollar, Trading.Data(listOf(BID(1f, 1f))))
        val tradingPairs = TradingPairs(listOf(tradingPair))
        val currencyStorage = getCurrencyStorage(Result.success(listOf(CryptoCurrency(CryptoProperties("BTC", "Bitcoin", "Bitcoin", "BTC", Ticker("BTC"))))))
        val tradingPairsRemoteSource = getTradingPairsRemoteSource(Result.success(tradingPairs))
        val tradingPairsMaker = TradingPairsMaker(currencyStorage)
        val getTradingPairsData = GetTradingPairsData(tradingPairsRemoteSource)
        val handlers = setOf(StartActionHandler(), tradingPairsMaker, getTradingPairsData, FilterTradingPairsBasedOnSearchingToken(DefaultCryptoMarketplaceSearchingStrategy()))
        val store = InternalCryptoMarketplaceStore(handlers)
        var receivedResultsNumber = 0
        var lastEmittedResult: CryptoMarketplaceResults? = null
        store.visitStore(CryptoMarketplaceResults()).collect {
            receivedResultsNumber++
            lastEmittedResult = it
        }
        receivedResultsNumber shouldBe 1
        val tradingPairsResults = lastEmittedResult.shouldNotBeNull().data().filterIsInstance<TradingPairs>().shouldNotBeNull().first()
        val tradingPairResults = tradingPairsResults.first()
        tradingPairResults.baseCurrency.shouldBeInstanceOf<CryptoCurrency>()
        tradingPairResults.quoteCurrency.shouldBeInstanceOf<UnitedStatesDollar>()
    }

    private fun getCurrencyStorage(returnedResults: Result<List<Currency<*>>>): CurrencyStorage {
        val currencyStorage = mockk<CurrencyStorage> {
            coEvery { retrieveCurrencies<Currency<*>>(any()) } returns returnedResults
        }
        return currencyStorage
    }

    private fun getTradingPairsRemoteSource(returnedResults: Result<TradingPairs>): TradingPairsRemoteSource {
        val remoteSource = mockk<TradingPairsRemoteSource> {
            coEvery { fetchTradingPairsData(any()) } returns returnedResults
        }
        return remoteSource
    }
}