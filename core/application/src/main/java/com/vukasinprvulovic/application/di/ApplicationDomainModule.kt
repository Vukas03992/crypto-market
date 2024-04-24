package com.vukasinprvulovic.application.di

import com.vukasinprvulovic.application.features.cryptomarketplace.store.di.CryptoMarketplaceStoreModule
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [CryptoMarketplaceStoreModule::class])
@InstallIn(SingletonComponent::class)
object ApplicationDomainModule
