@file:Suppress("PackageDirectoryMismatch")
package com.vukasinprvulovic.application.entities.currency

import com.vukasinprvulovic.application.entities.currency.types.crypto.properties.CryptoProperties
import com.vukasinprvulovic.application.entities.ticker.HasTicker

sealed class BaseCryptoCurrency<out CryptoSpecificProperties: BaseCryptoCurrency.Properties>(
    override val properties: CryptoSpecificProperties
): Currency<CryptoSpecificProperties>() {

    interface Properties: Currency.Properties, HasTicker
}

data class CryptoCurrency(override val properties: CryptoProperties): BaseCryptoCurrency<BaseCryptoCurrency.Properties>(properties), BaseCryptoCurrency.Properties by properties
