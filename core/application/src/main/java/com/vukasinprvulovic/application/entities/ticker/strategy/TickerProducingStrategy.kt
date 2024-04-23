package com.vukasinprvulovic.application.entities.ticker.strategy

import com.vukasinprvulovic.application.entities.ticker.HasTicker
import com.vukasinprvulovic.application.entities.ticker.Ticker

interface TickerProducingStrategy<in T> {

    fun produceTicker(materials: T): Ticker

    object Default: TickerProducingStrategy<HasTicker> {
        override fun produceTicker(materials: HasTicker): Ticker {
            return materials.ticker
        }
    }
}

interface HasTickerProducer: HasTicker {
    @Suppress("UNCHECKED_CAST")
    fun <T: HasTicker> ticker(strategy: TickerProducingStrategy<T>? = null): Ticker {
        return strategy?.produceTicker(this as T) ?: TickerProducingStrategy.Default.produceTicker(this as T)
    }
}