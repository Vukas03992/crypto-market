package com.vukasinprvulovic.storage.data.currency

import com.vukasinprvulovic.storage.data.currency.types.Crypto
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CurrencyStorageCryptoTests {

    @Test
    fun `when crypto currencies are requested then crypto storage returns list of currencies`() = runTest {
        val crypto = Crypto()
        val currenciesResult = crypto.getFilteredCurrencies()
        currenciesResult.isSuccess shouldBe true
        val currencies = currenciesResult.getOrNull().shouldNotBeNull()
        currencies.size shouldBe 20
        val bitcoin = currencies.first()
        bitcoin.identifier shouldBe "BTC"
        bitcoin.name shouldBe "Bitcoin"
        bitcoin.symbol shouldBe "BTC"
        bitcoin.isoCode shouldBe "BTC"
        bitcoin.ticker.symbol shouldBe "BTC"
    }
}
