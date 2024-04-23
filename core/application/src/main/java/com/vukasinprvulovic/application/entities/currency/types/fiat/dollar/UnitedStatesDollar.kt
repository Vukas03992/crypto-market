@file:Suppress("PackageDirectoryMismatch")

package com.vukasinprvulovic.application.entities.currency

data object UnitedStatesDollar: BaseFiatCurrency<UnitedStatesDollarProperties>(UnitedStatesDollarProperties)

data object UnitedStatesDollarProperties: BaseFiatCurrency.Properties(
    name = "United States Dollar",
    symbol = "$",
    isoCode = "USD"
)
