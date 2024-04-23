@file:Suppress("PackageDirectoryMismatch")
package com.vukasinprvulovic.application.entities.currency

sealed class BaseFiatCurrency<out FiatSpecificProperties: BaseFiatCurrency.Properties>(
    override val properties: FiatSpecificProperties
): Currency<FiatSpecificProperties>(), Currency.Properties by properties {

    open class Properties(override val name: String, override val symbol: String, override val isoCode: String) : Currency.Properties

    data class FiatCurrency(override val properties: Properties): BaseFiatCurrency<Properties>(properties)
}
