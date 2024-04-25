package com.vukasinprvulovic.application.entities.currency.types.crypto.properties

import com.vukasinprvulovic.application.entities.currency.BaseCryptoCurrency
import com.vukasinprvulovic.application.entities.ticker.Ticker
import kotlinx.serialization.Serializable

@Serializable
data class CryptoProperties(
    override val identifier: String,
    override val name: String,
    override val symbol: String,
    override val icon: String?,
    override val isoCode: String,
    override val ticker: Ticker
): BaseCryptoCurrency.Properties
