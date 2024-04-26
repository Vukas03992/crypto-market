package com.vukasinprvulovic.application.feature.cryptomarketplace.store.unit.components

import com.vukasinprvulovic.application.data.sources.remote.trading.pairs.TradingPairsRemoteSource
import com.vukasinprvulovic.application.entities.currency.UnitedStatesDollar
import com.vukasinprvulovic.application.entities.trading.data.Trading
import com.vukasinprvulovic.application.entities.trading.data.price.bid.BID
import com.vukasinprvulovic.application.entities.trading.pair.TradingPair
import com.vukasinprvulovic.application.entities.trading.pair.TradingPairs
import com.vukasinprvulovic.application.features.cryptomarketplace.result.CryptoMarketplaceResults
import com.vukasinprvulovic.application.features.cryptomarketplace.result.requireDataOfInstance
import com.vukasinprvulovic.application.features.cryptomarketplace.store.CryptoMarketplaceStore
import com.vukasinprvulovic.application.features.cryptomarketplace.store.actions.CryptoMarketplaceStoreAction
import com.vukasinprvulovic.application.features.cryptomarketplace.store.components.GetTradingPairsData
import com.vukasinprvulovic.application.features.cryptomarketplace.store.components.TradingPairsAreMade
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Test

class GetTradingPairsDataTests {

    @Test
    fun `when get trading pairs data is executed then results are emitted to CryptoMarketplace`() = runTest {
        val tradingPairsRemoteSource = mockk<TradingPairsRemoteSource>()
        val tradingData = Trading.Data(
            listOf(BID(1f, 1f))
        )
        val tradingPairs = TradingPairs(listOf(TradingPair(UnitedStatesDollar, UnitedStatesDollar, Trading.Data(emptyList()))))
        coEvery { tradingPairsRemoteSource.fetchTradingPairsData(any()) } returns Result.success(TradingPairs(tradingPairs.map { it.copy(tradingData = tradingData) }))
        val getTradingPairsData = GetTradingPairsData(tradingPairsRemoteSource)
        val context = CryptoMarketplaceStore.Context(CryptoMarketplaceStoreAction.GetTradingPairsData, CryptoMarketplaceResults(setOf(TradingPairsAreMade(tradingPairs.copy()))))
        var results: CryptoMarketplaceResults? = null
        flow {
            getTradingPairsData.handle(context, this)
        }.collect {
            results = it
        }
        val pairs = results.shouldNotBeNull().data().requireDataOfInstance<TradingPairs>()
        pairs.shouldNotBeEmpty().first().shouldBeInstanceOf<TradingPair<UnitedStatesDollar, UnitedStatesDollar>>().tradingData shouldBe tradingData
    }
}