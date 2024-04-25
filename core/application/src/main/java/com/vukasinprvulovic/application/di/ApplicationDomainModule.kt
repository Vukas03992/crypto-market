package com.vukasinprvulovic.application.di

import com.vukasinprvulovic.application.features.cryptomarketplace.di.CryptoMarketplaceStoreBindsModule
import com.vukasinprvulovic.application.features.cryptomarketplace.di.CryptoMarketplaceStoreModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [CryptoMarketplaceStoreModule::class, CryptoMarketplaceStoreBindsModule::class])
@InstallIn(SingletonComponent::class)
object ApplicationDomainModule
