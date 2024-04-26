package com.vukasinprvulovic.application.entities.trading.data.price.bid

import com.vukasinprvulovic.application.entities.trading.data.TradingData

/**
 * The highest price a buyer is willing to pay for a specific financial asset at a given time
 **/
data class BID(
    val value: Float,
    val size: Float
): TradingData
