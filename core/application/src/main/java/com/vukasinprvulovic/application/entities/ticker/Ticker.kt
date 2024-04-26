package com.vukasinprvulovic.application.entities.ticker

import kotlinx.serialization.Serializable

@Serializable
data class Ticker(
    val symbol: String
)

interface HasTicker {
    val ticker: Ticker
}
