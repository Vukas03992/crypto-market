@file:Suppress("PackageDirectoryMismatch")
package com.vukasinprvulovic.application.entities.currency

sealed class BaseCryptoCurrency<out CryptoSpecificProperties: BaseCryptoCurrency.Properties>(
    override val properties: CryptoSpecificProperties
): Currency<CryptoSpecificProperties>() {

    open class Properties(
        override val name: String,
        override val symbol: String,
        override val isoCode: String
    ): Currency.Properties

    data class CryptoCurrency(override val properties: Properties): BaseCryptoCurrency<Properties>(properties)
}
