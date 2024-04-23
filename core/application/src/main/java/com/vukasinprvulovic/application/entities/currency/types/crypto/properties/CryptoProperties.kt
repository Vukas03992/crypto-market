package com.vukasinprvulovic.application.entities.currency.types.crypto.properties

import com.vukasinprvulovic.application.entities.currency.BaseCryptoCurrency
import com.vukasinprvulovic.application.entities.ticker.Ticker

data class CryptoProperties(
    override val name: String,
    override val symbol: String,
    override val isoCode: String,
    override val ticker: Ticker
): BaseCryptoCurrency.Properties
