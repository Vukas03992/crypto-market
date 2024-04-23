package com.vukasinprvulovic.application.entities.currency

sealed class Currency<out SpecificProperties: Currency.Properties> {
    abstract val properties: SpecificProperties

    interface Properties {
        val name: String
        val symbol: String
        val isoCode: String
    }
}
