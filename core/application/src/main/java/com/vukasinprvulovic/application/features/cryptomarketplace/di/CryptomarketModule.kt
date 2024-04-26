package com.vukasinprvulovic.application.features.cryptomarketplace.di

import com.vukasinprvulovic.application.features.cryptomarketplace.refreshing.conditions.CryptoMarketplaceRefreshingConditions
import com.vukasinprvulovic.application.features.cryptomarketplace.refreshing.conditions.connectivity.InternetConnectionRefreshingCondition
import com.vukasinprvulovic.application.features.cryptomarketplace.searching.strategy.CryptoMarketplaceSearchingStrategy
import com.vukasinprvulovic.application.features.cryptomarketplace.searching.strategy.DefaultCryptoMarketplaceSearchingStrategy
import com.vukasinprvulovic.application.features.cryptomarketplace.store.CryptoMarketplaceStore
import com.vukasinprvulovic.application.features.cryptomarketplace.store.InternalCryptoMarketplaceStore
import com.vukasinprvulovic.application.features.cryptomarketplace.store.actions.CryptoMarketplaceStoreActionHandlers
import com.vukasinprvulovic.application.features.cryptomarketplace.store.actions.StartActionHandler
import com.vukasinprvulovic.application.features.cryptomarketplace.store.components.FilterTradingPairsBasedOnSearchingToken
import com.vukasinprvulovic.application.features.cryptomarketplace.store.components.GetTradingPairsData
import com.vukasinprvulovic.application.features.cryptomarketplace.store.components.TradingPairsMaker
import dagger.Binds
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

@Module
@InstallIn(SingletonComponent::class)
internal object CryptoMarketplaceStoreModule {

    @Provides
    fun provideCryptoMarketplaceStoreActionHandlers(
        startActionHandler: StartActionHandler,
        tradingPairsMaker: TradingPairsMaker,
        getTradingPairsData: GetTradingPairsData,
        filterTradingPairsBasedOnSearchingToken: FilterTradingPairsBasedOnSearchingToken
    ): CryptoMarketplaceStoreActionHandlers = CryptoMarketplaceStoreActionHandlers(
        setOf(
            startActionHandler,
            tradingPairsMaker,
            getTradingPairsData,
            filterTradingPairsBasedOnSearchingToken
        )
    )

    @Provides
    fun provideCryptoMarketplaceSearchingStrategy(defaultCryptoMarketplaceSearchingStrategy: DefaultCryptoMarketplaceSearchingStrategy): CryptoMarketplaceSearchingStrategy = defaultCryptoMarketplaceSearchingStrategy
}

@Module
@InstallIn(SingletonComponent::class)
internal abstract class CryptoMarketplaceStoreBindsModule {

    @Binds
    abstract fun bindCryptoMarketplaceStore(internalCryptoMarketplaceStore: InternalCryptoMarketplaceStore): CryptoMarketplaceStore
}
