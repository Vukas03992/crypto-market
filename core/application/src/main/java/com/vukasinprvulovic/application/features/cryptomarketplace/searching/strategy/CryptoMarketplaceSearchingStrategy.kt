package com.vukasinprvulovic.application.features.cryptomarketplace.searching.strategy

import androidx.annotation.VisibleForTesting
import com.vukasinprvulovic.application.entities.trading.pair.TradingPair
import javax.inject.Inject

interface CryptoMarketplaceSearchingStrategy {
    suspend fun matches(searchingToken: String, tradingPair: TradingPair<*,*>): Boolean
}

internal class DefaultCryptoMarketplaceSearchingStrategy @Inject constructor(): CryptoMarketplaceSearchingStrategy {
    override suspend fun matches(searchingToken: String, tradingPair: TradingPair<*, *>): Boolean {
        return containsAllCharacters(tradingPair.baseCurrency.properties.name, searchingToken)
    }

    @VisibleForTesting
    fun containsAllCharacters(source: String, target: String): Boolean {
        val sourceCounts = source.lowercase().groupingBy { it }.eachCount()
        val targetCounts = target.lowercase().groupingBy { it }.eachCount()
        return targetCounts.all { (char, count) ->
            sourceCounts.getOrDefault(char, 0) >= count
        }
    }
}