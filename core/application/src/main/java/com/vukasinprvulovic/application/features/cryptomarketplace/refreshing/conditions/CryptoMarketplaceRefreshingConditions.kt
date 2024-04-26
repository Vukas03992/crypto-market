package com.vukasinprvulovic.application.features.cryptomarketplace.refreshing.conditions

import com.vukasinprvulovic.application.features.cryptomarketplace.result.CryptoMarketplaceResults
import javax.inject.Inject

class CryptoMarketplaceRefreshingConditions @Inject constructor(
    private val allConditions: All
) {
    private val subscribers = mutableSetOf<CryptoMarketplaceRefreshingCondition.Subscriber>()
    init {
        allConditions.set.forEach {
            it.subscribeToConditionChanges { subscribers.forEach { it.onConditionChanged() } }
        }
    }

    suspend fun areConditionsMet(): Results {
        val conditionResults: List<Data?> = allConditions.set.map { it.isConditionMet() }
        return Results(conditionResults.filterNotNull())
    }

    fun subscribeToConditionChanges(subscriber: CryptoMarketplaceRefreshingCondition.Subscriber) {
        subscribers.add(subscriber)
    }

    data class All(
        val set: Set<CryptoMarketplaceRefreshingCondition>
    )

    interface Data

    data class Results(
        val conditions: List<Data>
    ): Data, CryptoMarketplaceResults.Error(null) {
        val areMet = conditions.isEmpty()
    }
}
