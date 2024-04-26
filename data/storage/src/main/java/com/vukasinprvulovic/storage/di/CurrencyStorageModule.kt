package com.vukasinprvulovic.storage.di

import com.vukasinprvulovic.storage.data.currency.types.Crypto
import com.vukasinprvulovic.storage.data.currency.types.CurrencyStorageFilterAppliers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CurrencyStorageModule {

    @Provides
    fun provideCurrencyStorageFilterAppliers(crypto: Crypto): CurrencyStorageFilterAppliers {
        return CurrencyStorageFilterAppliers(setOf(crypto))
    }
}
