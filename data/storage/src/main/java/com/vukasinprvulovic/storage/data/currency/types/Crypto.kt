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
    {"identifier": "BTC", "name": "Bitcoin", "symbol": "BTC", "isoCode": "BTC", "ticker": {"symbol": "BTC"}, "icon": "https://bitcoin.org/img/icons/opengraph.png"},
    {"identifier": "ETH", "name": "Ethereum", "symbol": "ETH", "isoCode": "ETH", "ticker": {"symbol": "ETH"}, "icon": "https://raw.githubusercontent.com/Pymmdrza/Cryptocurrency_Logos/mainx/PNG/eth.png"},
    {"identifier": "CHSB", "name": "SwissBorg", "symbol": "CHSB", "isoCode": "CHSB", "ticker": {"symbol": "CHSB"}, "icon": "https://raw.githubusercontent.com/Pymmdrza/Cryptocurrency_Logos/mainx/PNG/chsb.png"},
    {"identifier": "LTC", "name": "Litecoin", "symbol": "LTC", "isoCode": "LTC", "ticker": {"symbol": "LTC"}, "icon": "https://raw.githubusercontent.com/Pymmdrza/Cryptocurrency_Logos/mainx/PNG/ltc.png"},
    {"identifier": "XRP", "name": "Ripple", "symbol": "XRP", "isoCode": "XRP", "ticker": {"symbol": "XRP"}, "icon": "https://raw.githubusercontent.com/Pymmdrza/Cryptocurrency_Logos/mainx/PNG/xrp.png"},
    {"identifier": "DSH", "name": "Dash", "symbol": "DASH", "isoCode": "DASH", "ticker": {"symbol": "DASH"}, "icon": "https://raw.githubusercontent.com/Pymmdrza/Cryptocurrency_Logos/mainx/PNG/dash.png"},
    {"identifier": "RRT", "name": "Recovery Right Tokens", "symbol": "RRT", "isoCode": "RRT", "ticker": {"symbol": "RRT"}, "icon": "https://assets.coingecko.com/coins/images/6502/standard/Recovery_Right_Token.png?1696506861"},
    {"identifier": "EOS", "name": "EOS.IO", "symbol": "EOS", "isoCode": "EOS", "ticker": {"symbol": "EOS"}, "icon": "https://raw.githubusercontent.com/Pymmdrza/Cryptocurrency_Logos/mainx/PNG/eos.png"},
    {"identifier": "SAN", "name": "Santiment", "symbol": "SAN", "isoCode": "SAN", "ticker": {"symbol": "SAN"}, "icon": "https://raw.githubusercontent.com/Pymmdrza/Cryptocurrency_Logos/mainx/PNG/san.png"},
    {"identifier": "DAT", "name": "Datum", "symbol": "DAT", "isoCode": "DAT", "ticker": {"symbol": "DAT"}, "icon": "https://raw.githubusercontent.com/Pymmdrza/Cryptocurrency_Logos/mainx/PNG/dat.png"},
    {"identifier": "SNT", "name": "Status", "symbol": "SNT", "isoCode": "SNT", "ticker": {"symbol": "SNT"}, "icon": "https://raw.githubusercontent.com/Pymmdrza/Cryptocurrency_Logos/mainx/PNG/snt.png"},
    {"identifier": "DOGE", "name": "Dogecoin", "symbol": "DOGE", "isoCode": "DOGE", "ticker": {"symbol": "DOGE"}, "icon": "https://raw.githubusercontent.com/Pymmdrza/Cryptocurrency_Logos/mainx/PNG/doge.png"},
    {"identifier": "LUNA", "name": "Terra", "symbol": "LUNA", "isoCode": "LUNA", "ticker": {"symbol": "LUNA"}, "icon": "https://raw.githubusercontent.com/Pymmdrza/Cryptocurrency_Logos/mainx/PNG/luna.png"},
    {"identifier": "MATIC", "name": "Polygon", "symbol": "MATIC", "isoCode": "MATIC", "ticker": {"symbol": "MATIC"}, "icon": "https://raw.githubusercontent.com/Pymmdrza/Cryptocurrency_Logos/mainx/PNG/matic.png"},
    {"identifier": "NEXO", "name": "Nexo", "symbol": "NEXO", "isoCode": "NEXO", "ticker": {"symbol": "NEXO"}, "icon": "https://s2.coinmarketcap.com/static/img/coins/64x64/2694.png"},
    {"identifier": "OCEAN", "name": "Ocean Protocol", "symbol": "OCEAN", "isoCode": "OCEAN", "ticker": {"symbol": "OCEAN"}, "icon": "https://raw.githubusercontent.com/Pymmdrza/Cryptocurrency_Logos/mainx/PNG/ocean.png"},
    {"identifier": "BEST", "name": "Bitpanda Ecosystem Token", "symbol": "BEST", "isoCode": "BEST", "ticker": {"symbol": "BEST"}, "icon": "https://s2.coinmarketcap.com/static/img/coins/64x64/4361.png"},
    {"identifier": "AAVE", "name": "Aave", "symbol": "AAVE", "isoCode": "AAVE", "ticker": {"symbol": "AAVE"}, "icon": "https://raw.githubusercontent.com/Pymmdrza/Cryptocurrency_Logos/mainx/PNG/aave.png"},
    {"identifier": "PLU", "name": "Pluton", "symbol": "PLU", "isoCode": "PLU", "ticker": {"symbol": "PLU"}, "icon": "https://dynamic-assets.coinbase.com/48008fa0b7bc354bcd3b87e6535ca0ac1b866e87b3445d2ca5730b3bfac5457b58eac774cf508b8f6b6818c24012818821308fe00f2555c729d5cfae068b8fee/asset_icons/eb03ba7582755c2ef7edda1d0992df6603a310417e761390c18ffd19b7fada3b.png"},
    {"identifier": "FIL", "name": "Filecoin", "symbol": "FIL", "isoCode": "FIL", "ticker": {"symbol": "FIL"}, "icon": "https://raw.githubusercontent.com/Pymmdrza/Cryptocurrency_Logos/mainx/PNG/fil.png"}
]
""".trimIndent()
