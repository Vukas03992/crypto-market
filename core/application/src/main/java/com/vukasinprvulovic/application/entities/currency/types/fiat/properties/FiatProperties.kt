package com.vukasinprvulovic.application.entities.currency.types.fiat.properties

import com.vukasinprvulovic.application.entities.currency.BaseFiatCurrency
import com.vukasinprvulovic.application.entities.ticker.Ticker

data class FiatProperties(
    override val name: String,
    override val symbol: String,
    override val icon: String?,
    override val isoCode: String,
    override val ticker: Ticker
): BaseFiatCurrency.Properties {
    override val identifier: String = isoCode
}
