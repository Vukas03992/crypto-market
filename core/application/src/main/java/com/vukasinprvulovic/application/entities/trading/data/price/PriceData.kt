package com.vukasinprvulovic.application.entities.trading.data.price

import com.vukasinprvulovic.application.entities.trading.data.TradingData

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
