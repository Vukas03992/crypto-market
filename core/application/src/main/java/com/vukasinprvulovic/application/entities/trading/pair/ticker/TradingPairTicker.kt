package com.vukasinprvulovic.application.entities.trading.pair.ticker

import com.vukasinprvulovic.application.entities.ticker.HasTicker
import com.vukasinprvulovic.application.entities.ticker.Ticker
import com.vukasinprvulovic.application.entities.ticker.strategy.TickerProducingStrategy
import com.vukasinprvulovic.application.entities.trading.pair.TradingPair

class TradingPairTickerProducingStrategy: TickerProducingStrategy<TradingPair<*,*>> {
    override fun produceTicker(materials: TradingPair<*, *>): Ticker {
        val baseCurrencyTicker = if (materials.baseCurrency is HasTicker) materials.baseCurrency.ticker.symbol else "N/A"
        val quoteCurrencyTicker = if (materials.quoteCurrency is HasTicker) materials.quoteCurrency.ticker.symbol else "N/A"
        return Ticker("$baseCurrencyTicker/${quoteCurrencyTicker}")
    }
}