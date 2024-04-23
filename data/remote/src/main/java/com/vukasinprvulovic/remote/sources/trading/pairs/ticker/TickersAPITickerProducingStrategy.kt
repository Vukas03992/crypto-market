package com.vukasinprvulovic.remote.sources.trading.pairs.ticker

import com.vukasinprvulovic.application.entities.ticker.HasTicker
import com.vukasinprvulovic.application.entities.ticker.Ticker
import com.vukasinprvulovic.application.entities.ticker.strategy.TickerProducingStrategy
import com.vukasinprvulovic.application.entities.trading.pair.TradingPair

class TickersAPITickerProducingStrategy: TickerProducingStrategy<TradingPair<*,*>> {
    override fun produceTicker(materials: TradingPair<*, *>): Ticker {
        val baseCurrency = materials.baseCurrency
        val baseCurrencyTicker = if (baseCurrency is HasTicker) baseCurrency.ticker.symbol else error("Base currency ticker not found!")
        val quoteCurrency = materials.quoteCurrency
        val quoteCurrencyTicker = if (quoteCurrency is HasTicker) quoteCurrency.ticker.symbol else error("Quote currency ticker not found!")
        return Ticker("t$baseCurrencyTicker${quoteCurrencyTicker}")
    }
}