package com.vukasinprvulovic.application.features.cryptomarketplace.di

import com.vukasinprvulovic.application.features.cryptomarketplace.refreshing.conditions.CryptoMarketplaceRefreshingConditions
import com.vukasinprvulovic.application.features.cryptomarketplace.refreshing.conditions.connectivity.InternetConnectionRefreshingCondition
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class CryptoMarketplaceModule {

    @Provides
    fun provideCryptoMarketplaceRefreshingConditions(
        internetConnectionRefreshingCondition: InternetConnectionRefreshingCondition
    ): CryptoMarketplaceRefreshingConditions.All {
        return CryptoMarketplaceRefreshingConditions.All(
            setOf(
                internetConnectionRefreshingCondition
            )
        )
    }
}