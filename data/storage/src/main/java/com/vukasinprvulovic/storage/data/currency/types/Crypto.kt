package com.vukasinprvulovic.storage.data.currency.types

import com.vukasinprvulovic.application.entities.currency.CryptoCurrency
import com.vukasinprvulovic.application.entities.currency.types.crypto.properties.CryptoProperties
import com.vukasinprvulovic.utils.kotlin.results.foldResultsSuspend
import kotlinx.serialization.json.Json
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Crypto @Inject constructor(): CurrencyStorageFilterApplier<CryptoCurrency> {
    override val retrievedClass: Class<CryptoCurrency> = CryptoCurrency::class.java

    override suspend fun getFilteredCurrencies(): Result<List<CryptoCurrency>> {
        return foldResultsSuspend {
            val cryptoCurrencyProperties = Json.decodeFromString<List<CryptoProperties>>(cryptoCurrenciesJson)
            val cryptoCurrencies = cryptoCurrencyProperties.map { CryptoCurrency(it) }
            Result.success(cryptoCurrencies)
        }
    }
}

/*
* This is compromise for the sake of the challenge. Normally, this would be fetched from the network or database/file.
* */
private val cryptoCurrenciesJson = """
    [
        {"identifier": "BTC", "name": "Bitcoin", "symbol": "BTC", "isoCode": "BTC", "ticker": {"symbol": "BTC"}},
        {"identifier": "ETH", "name": "Ethereum", "symbol": "ETH", "isoCode": "ETH", "ticker": {"symbol": "ETH"}},
        {"identifier": "CHSB", "name": "SwissBorg", "symbol": "CHSB", "isoCode": "CHSB", "ticker": {"symbol": "CHSB"}},
        {"identifier": "LTC", "name": "Litecoin", "symbol": "LTC", "isoCode": "LTC", "ticker": {"symbol": "LTC"}},
        {"identifier": "XRP", "name": "Ripple", "symbol": "XRP", "isoCode": "XRP", "ticker": {"symbol": "XRP"}},
        {"identifier": "DSH", "name": "Dash", "symbol": "DASH", "isoCode": "DASH", "ticker": {"symbol": "DASH"}},
        {"identifier": "RRT", "name": "Recovery Right Tokens", "symbol": "RRT", "isoCode": "RRT", "ticker": {"symbol": "RRT"}},
        {"identifier": "EOS", "name": "EOS.IO", "symbol": "EOS", "isoCode": "EOS", "ticker": {"symbol": "EOS"}},
        {"identifier": "SAN", "name": "Santiment", "symbol": "SAN", "isoCode": "SAN", "ticker": {"symbol": "SAN"}},
        {"identifier": "DAT", "name": "Datum", "symbol": "DAT", "isoCode": "DAT", "ticker": {"symbol": "DAT"}},
        {"identifier": "SNT", "name": "Status", "symbol": "SNT", "isoCode": "SNT", "ticker": {"symbol": "SNT"}},
        {"identifier": "DOGE", "name": "Dogecoin", "symbol": "DOGE", "isoCode": "DOGE", "ticker": {"symbol": "DOGE"}},
        {"identifier": "LUNA", "name": "Terra", "symbol": "LUNA", "isoCode": "LUNA", "ticker": {"symbol": "LUNA"}},
        {"identifier": "MATIC", "name": "Polygon", "symbol": "MATIC", "isoCode": "MATIC", "ticker": {"symbol": "MATIC"}},
        {"identifier": "NEXO", "name": "Nexo", "symbol": "NEXO", "isoCode": "NEXO", "ticker": {"symbol": "NEXO"}},
        {"identifier": "OCEAN", "name": "Ocean Protocol", "symbol": "OCEAN", "isoCode": "OCEAN", "ticker": {"symbol": "OCEAN"}},
        {"identifier": "BEST", "name": "Bitpanda Ecosystem Token", "symbol": "BEST", "isoCode": "BEST", "ticker": {"symbol": "BEST"}},
        {"identifier": "AAVE", "name": "Aave", "symbol": "AAVE", "isoCode": "AAVE", "ticker": {"symbol": "AAVE"}},
        {"identifier": "PLU", "name": "Pluton", "symbol": "PLU", "isoCode": "PLU", "ticker": {"symbol": "PLU"}},
        {"identifier": "FIL", "name": "Filecoin", "symbol": "FIL", "isoCode": "FIL", "ticker": {"symbol": "FIL"}}
    ]
""".trimIndent()
