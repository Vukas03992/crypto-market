package com.vukasinprvulovic.application.entities.trading.data.price

import com.vukasinprvulovic.application.entities.trading.data.Trading
import com.vukasinprvulovic.application.entities.trading.data.TradingData
import com.vukasinprvulovic.application.entities.trading.data.price.ask.ASK
import com.vukasinprvulovic.application.entities.trading.data.price.bid.BID

sealed class Price: TradingData {

    sealed class Change: Price() {
        data class Daily(
            val amountChangedSinceYesterday: Float,
            val percentageChangedSinceYesterday: Float
        ): Change()
    }

    data class LastTrade(
        val amount: Float
    ): Price()

    sealed class DailyMetrics: Price() {
        /**
         * The highest price at which a financial asset was traded during one trading day.
         **/
        data class High(
            val amount: Float
        ): DailyMetrics()

        /**
         * The lowest price at which a financial asset traded during the trading day.
         **/
        data class Low(
            val amount: Float
        ): DailyMetrics()
    }
}

fun Trading.Data.price(): Float {
    val bid = getData<BID>().value
    val ask = getData<ASK>().value
    return (bid + ask) / 2
}
