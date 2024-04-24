@file:Suppress("PackageDirectoryMismatch")

package com.vukasinprvulovic.application.entities.currency

import com.vukasinprvulovic.application.entities.currency.types.fiat.properties.FiatProperties
import com.vukasinprvulovic.application.entities.ticker.Ticker

data object UnitedStatesDollar: BaseFiatCurrency<UnitedStatesDollarProperties>(UnitedStatesDollarProperties), BaseFiatCurrency.Properties by UnitedStatesDollarProperties

data object UnitedStatesDollarProperties: BaseFiatCurrency.Properties {
    override val name: String = "United States Dollar"
    override val symbol: String = "$"
    override val isoCode: String = "USD"
    override val ticker: Ticker = Ticker(isoCode)
    override val identifier: String = isoCode
}
