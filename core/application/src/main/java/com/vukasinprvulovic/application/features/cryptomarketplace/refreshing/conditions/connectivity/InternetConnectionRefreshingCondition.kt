package com.vukasinprvulovic.application.features.cryptomarketplace.refreshing.conditions.connectivity

import com.vukasinprvulovic.application.components.device.connectivity.internet.InternetConnectionStatus
import com.vukasinprvulovic.application.features.cryptomarketplace.refreshing.conditions.CryptoMarketplaceRefreshingCondition
import com.vukasinprvulovic.application.features.cryptomarketplace.refreshing.conditions.CryptoMarketplaceRefreshingConditions
import javax.inject.Inject

class InternetConnectionRefreshingCondition @Inject constructor(
    private val internetConnectionStatus: InternetConnectionStatus
): CryptoMarketplaceRefreshingCondition {
    override suspend fun isConditionMet(): CryptoMarketplaceRefreshingConditions.Data? {
        return if (internetConnectionStatus.isConnected) null else NoInternetConnection
    }

    override fun subscribeToConditionChanges(subscriber: CryptoMarketplaceRefreshingCondition.Subscriber) {
        internetConnectionStatus.subscribeToInternetConnectionEvents { subscriber.onConditionChanged() }
    }
}

data object NoInternetConnection: CryptoMarketplaceRefreshingConditions.Data