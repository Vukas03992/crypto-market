package com.vukasinprvulovic.application.features.cryptomarketplace.refreshing.conditions

interface CryptoMarketplaceRefreshingCondition {
    suspend fun isConditionMet(): CryptoMarketplaceRefreshingConditions.Data?
    fun subscribeToConditionChanges(subscriber: Subscriber)

    fun interface Subscriber {
        fun onConditionChanged()
    }
}