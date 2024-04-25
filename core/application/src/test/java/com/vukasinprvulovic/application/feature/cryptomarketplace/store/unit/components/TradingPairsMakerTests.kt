package com.vukasinprvulovic.application.feature.cryptomarketplace.store.unit.components

import com.vukasinprvulovic.application.data.sources.storage.currency.CurrencyStorage
import com.vukasinprvulovic.application.entities.currency.CryptoCurrency
import com.vukasinprvulovic.application.entities.currency.Currency
import com.vukasinprvulovic.application.entities.currency.UnitedStatesDollar
import com.vukasinprvulovic.application.entities.currency.types.crypto.properties.CryptoProperties
import com.vukasinprvulovic.application.entities.ticker.Ticker
import com.vukasinprvulovic.application.entities.trading.pair.TradingPair
import com.vukasinprvulovic.application.features.cryptomarketplace.result.CryptoMarketplaceResults
import com.vukasinprvulovic.application.features.cryptomarketplace.result.requireDataOfInstance
import com.vukasinprvulovic.application.features.cryptomarketplace.store.CryptoMarketplaceStore
import com.vukasinprvulovic.application.features.cryptomarketplace.store.actions.CryptoMarketplaceStoreAction
import com.vukasinprvulovic.application.features.cryptomarketplace.store.components.TradingPairsMaker
import com.vukasinprvulovic.application.features.cryptomarketplace.store.components.TradingPairsAreMade
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

class TradingPairsMakerTests {

    @Test
    fun `when trading pairs maker is executed, no results are emitted since it is internal action`() = runTest {
        val currencyStorage = mockk<CurrencyStorage>()
        coEvery { currencyStorage.retrieveCurrencies<Currency<*>>(any()) } returns Result.success(emptyList())
        val tradingPairsMaker = TradingPairsMaker(currencyStorage)
        val context = CryptoMarketplaceStore.Context(CryptoMarketplaceStoreAction.MakeTradingPairs, CryptoMarketplaceResults())
        var results: CryptoMarketplaceResults? = null
        flow {
            tradingPairsMaker.handle(context, this)
        }.collect {
            results = it
        }
        results.shouldBeNull()
    }

    @Test
    fun `when trading pairs maker is executed with all expected inputs then context contains action handing results`() = runTest {
        val currencyStorage = mockk<CurrencyStorage>()
        coEvery { currencyStorage.retrieveCurrencies<Currency<*>>(any()) } returns Result.success(listOf(CryptoCurrency(CryptoProperties("BTC", "Bitcoin", "Bitcoin", "BTC", "",Ticker(symbol = "BTC")))))
        val tradingPairsMaker = TradingPairsMaker(currencyStorage)
        val context = CryptoMarketplaceStore.Context(CryptoMarketplaceStoreAction.MakeTradingPairs, CryptoMarketplaceResults())
        flow {
            tradingPairsMaker.handle(context, this)
        }.collect {}
        val internalActionResults = context.currentCryptoMarketplaceResults.data().requireDataOfInstance<TradingPairsAreMade>()
        internalActionResults.tradingPairs.size shouldBe 1
        val tradingPair = internalActionResults.tradingPairs.first().shouldBeInstanceOf<TradingPair<CryptoCurrency, UnitedStatesDollar>>()
        tradingPair.baseCurrency.identifier shouldBe "BTC"
        tradingPair.quoteCurrency.identifier shouldBe "USD"
    }

    @Test
    fun `when trading pairs maker is executed but currency storage fails then context contains error and no action results`() = runTest {
        val currencyStorage = mockk<CurrencyStorage>()
        coEvery { currencyStorage.retrieveCurrencies<Currency<*>>(any()) } returns Result.failure(IllegalStateException("Currency storage failed"))
        val tradingPairsMaker = TradingPairsMaker(currencyStorage)
        val context = CryptoMarketplaceStore.Context(CryptoMarketplaceStoreAction.MakeTradingPairs, CryptoMarketplaceResults())
        flow {
            tradingPairsMaker.handle(context, this)
        }.collect {}
        context.currentCryptoMarketplaceResults.data().shouldBeEmpty()
        val error = context.currentCryptoMarketplaceResults.errors().shouldNotBeEmpty().firstOrNull().shouldBeInstanceOf<CryptoMarketplaceResults.Error>()
        error.cause.shouldNotBeNull().message shouldBe "Currency storage failed"
    }
}
