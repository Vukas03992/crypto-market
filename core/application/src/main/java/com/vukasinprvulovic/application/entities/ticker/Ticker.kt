package com.vukasinprvulovic.application.entities.ticker

data class Ticker(
    val symbol: String
)

interface HasTicker {
    val ticker: Ticker
}
