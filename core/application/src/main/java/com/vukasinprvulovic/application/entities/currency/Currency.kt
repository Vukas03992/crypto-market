package com.vukasinprvulovic.application.entities.currency

sealed class Currency<out SpecificProperties: Currency.Properties> {
    abstract val properties: SpecificProperties

    interface Properties {
        val identifier: String
        val name: String
        val icon: String?
        val symbol: String
        val isoCode: String
    }
}

fun <SpecificProperties1: Currency.Properties, SpecificProperties2: Currency.Properties> Currency<SpecificProperties1>.isSameCurrencyAs(other: Currency<SpecificProperties2>): Boolean {
    return this.properties.identifier == other.properties.identifier
}
