package com.vukasinprvulovic.application.features.cryptomarketplace.store.di

import com.vukasinprvulovic.application.features.cryptomarketplace.store.CryptoMarketplaceStore
import com.vukasinprvulovic.application.features.cryptomarketplace.store.InternalCryptoMarketplaceStore
import com.vukasinprvulovic.application.features.cryptomarketplace.store.actions.CryptoMarketplaceStoreActionHandler
import com.vukasinprvulovic.application.features.cryptomarketplace.store.actions.OpenActionHandler
import com.vukasinprvulovic.application.features.cryptomarketplace.store.components.CryptoMarketplaceTradingPairsMaker
import com.vukasinprvulovic.application.features.cryptomarketplace.store.components.GetTradingPairsData
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
internal abstract class CryptoMarketplaceStoreModule {

    @Binds
    @IntoSet
    abstract fun provideOpenActionHandler(openActionHandler: OpenActionHandler): CryptoMarketplaceStoreActionHandler

    @Binds
    @IntoSet
    abstract fun provideMakeTradingPairsActionHandler(cryptoMarketplaceTradingPairsMaker: CryptoMarketplaceTradingPairsMaker): CryptoMarketplaceStoreActionHandler

    @Binds
    @IntoSet
    abstract fun provideGetTradingPairsDataActionHandler(getTradingPairsData: GetTradingPairsData): CryptoMarketplaceStoreActionHandler

    @Binds
    abstract fun bindCryptoMarketplaceStore(internalCryptoMarketplaceStore: InternalCryptoMarketplaceStore): CryptoMarketplaceStore
}