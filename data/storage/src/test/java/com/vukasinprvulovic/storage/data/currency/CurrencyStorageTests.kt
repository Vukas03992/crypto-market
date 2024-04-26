@file:OptIn(ExperimentalStdlibApi::class)

package com.vukasinprvulovic.storage.data.currency

import com.vukasinprvulovic.application.data.sources.storage.currency.filters.CryptoCurrencyStorageFilter
import com.vukasinprvulovic.storage.data.currency.types.Crypto
import com.vukasinprvulovic.storage.data.currency.types.CurrencyStorageFilterAppliers
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CurrencyStorageTests {

    @Test
    fun `when crypto currencies are requested by passing proper filet then crypto currencies are returned`() = runTest {
        val dispatcher = this.coroutineContext[CoroutineDispatcher]!!
        val currencyStorage = CurrencyStorageImplementation(dispatcher, CurrencyStorageFilterAppliers(setOf(Crypto())))
        val cryptoCurrencyResults = currencyStorage.retrieveCurrencies(CryptoCurrencyStorageFilter())
        cryptoCurrencyResults.isSuccess shouldBe true
        val cryptoCurrencies = cryptoCurrencyResults.getOrNull().shouldNotBeNull()
        cryptoCurrencies.size shouldBe 20
        val bitcoin = cryptoCurrencies.first()
        bitcoin.identifier shouldBe "BTC"
        bitcoin.name shouldBe "Bitcoin"
        bitcoin.symbol shouldBe "BTC"
        bitcoin.isoCode shouldBe "BTC"
        bitcoin.ticker.symbol shouldBe "BTC"
    }
}
