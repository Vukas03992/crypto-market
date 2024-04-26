package com.vukasinprvulovic.application.entities.trading.pair.utils

import com.vukasinprvulovic.application.entities.currency.Currency
import com.vukasinprvulovic.application.entities.trading.data.price.Price
import com.vukasinprvulovic.application.entities.trading.data.price.ask.ASK
import com.vukasinprvulovic.application.entities.trading.data.price.bid.BID
import com.vukasinprvulovic.application.entities.trading.data.units.DailyVolume
import com.vukasinprvulovic.application.entities.trading.pair.TradingPair

fun <BaseCurrency: Currency<*>, QuoteCurrency: Currency<*>> TradingPair<BaseCurrency, QuoteCurrency>.getDailyPriceChangeSinceYesterday(): Price.Change.Daily {
    return tradingData.getData()
}

fun <BaseCurrency: Currency<*>, QuoteCurrency: Currency<*>> TradingPair<BaseCurrency, QuoteCurrency>.getLastTradePrice(): Price.LastTrade {
    return tradingData.getData()
}

fun <BaseCurrency: Currency<*>, QuoteCurrency: Currency<*>> TradingPair<BaseCurrency, QuoteCurrency>.getDailyMetrics(): List<Price.DailyMetrics> {
    return tradingData.getAllData()
}

fun <BaseCurrency: Currency<*>, QuoteCurrency: Currency<*>> TradingPair<BaseCurrency, QuoteCurrency>.getBID(): BID {
    return tradingData.getData()
}

fun <BaseCurrency: Currency<*>, QuoteCurrency: Currency<*>> TradingPair<BaseCurrency, QuoteCurrency>.getASK(): ASK {
    return tradingData.getData()
}

fun <BaseCurrency: Currency<*>, QuoteCurrency: Currency<*>> TradingPair<BaseCurrency, QuoteCurrency>.getDailyVolume(): DailyVolume {
    return tradingData.getData()
}
