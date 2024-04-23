@file:Suppress("PackageDirectoryMismatch")
package com.vukasinprvulovic.application.entities.currency

import com.vukasinprvulovic.application.entities.currency.types.fiat.properties.FiatProperties
import com.vukasinprvulovic.application.entities.ticker.HasTicker

sealed class BaseFiatCurrency<out FiatSpecificProperties: BaseFiatCurrency.Properties>(
    override val properties: FiatSpecificProperties
): Currency<FiatSpecificProperties>() {

    interface Properties : Currency.Properties, HasTicker

    data class FiatCurrency(override val properties: FiatProperties): BaseFiatCurrency<FiatProperties>(properties), Properties by properties
}
