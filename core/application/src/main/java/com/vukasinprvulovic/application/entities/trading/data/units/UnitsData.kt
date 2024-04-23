package com.vukasinprvulovic.application.entities.trading.data.units

import com.vukasinprvulovic.application.entities.trading.data.TradingData

/**
 * The total number of units of a particular financial asset that were traded during one trading day.
 * */
data class DailyVolume(
    val amount: Float
): TradingData
