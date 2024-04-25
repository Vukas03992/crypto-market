package com.vukasinprvulovic.application.entities.trading.pair

import com.vukasinprvulovic.application.entities.currency.Currency
import com.vukasinprvulovic.application.entities.ticker.Ticker
import com.vukasinprvulovic.application.entities.ticker.strategy.HasTickerProducer
import com.vukasinprvulovic.application.entities.trading.data.Trading
import com.vukasinprvulovic.application.entities.trading.pair.ticker.TradingPairTickerProducingStrategy
import com.vukasinprvulovic.application.features.cryptomarketplace.result.CryptoMarketplaceResults

data class TradingPair<out BaseCurrency: Currency<*>, out QuoteCurrency: Currency<*>>(
    val baseCurrency: BaseCurrency,
    val quoteCurrency: QuoteCurrency,
    val tradingData: Trading.Data
): HasTickerProducer {
    override val ticker: Ticker
        get() = ticker(TradingPairTickerProducingStrategy())
}

data class TradingPairs(
    private val tradingPairs: List<TradingPair<*, *>>
): List<TradingPair<*, *>> by tradingPairs, CryptoMarketplaceResults.FinalData {

    inline fun <reified QuoteCurrency: Currency<*>> getTradingPairsWithQuoteCurrency(): List<TradingPair<*, QuoteCurrency>> {
        return filterIsInstance<TradingPair<*, QuoteCurrency>>()
    }

    inline fun <reified BaseCurrency: Currency<*>> getTradingPairsWithBaseCurrency(): List<TradingPair<BaseCurrency, *>> {
        return filterIsInstance<TradingPair<BaseCurrency, *>>()
    }

    inline fun <reified BaseCurrency: Currency<*>, reified QuoteCurrency: Currency<*>> findTradingPair(): TradingPair<BaseCurrency, QuoteCurrency>? {
        return filterIsInstance<TradingPair<BaseCurrency, QuoteCurrency>>().firstOrNull()
    }

    inline fun <reified BaseCurrency: Currency<*>, reified QuoteCurrency: Currency<*>> requireTradingPair(): TradingPair<BaseCurrency, QuoteCurrency> {
        return findTradingPair() ?: error("TradingPair(baseCurrency = ${BaseCurrency::class.simpleName}, quoteCurrency = ${QuoteCurrency::class.simpleName}) not found")
    }
}
