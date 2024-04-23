package com.vukasinprvulovic.application.entities.trading.data.price.ask

import com.vukasinprvulovic.application.entities.trading.data.TradingData

/**
 * The lowest price a seller is willing to accept for a specific financial asset at a given time
 **/
data class ASK(
    val value: Float,
    val size: Float
): TradingData
