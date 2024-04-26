package com.vukasinprvulovic.storage.di

import com.vukasinprvulovic.application.data.sources.storage.currency.CurrencyStorage
import com.vukasinprvulovic.storage.data.currency.CurrencyStorageImplementation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object StorageModule {

    @Provides
    fun bindCurrencyStorage(currencyStorageImplementation: CurrencyStorageImplementation) : CurrencyStorage {
        return currencyStorageImplementation
    }
}
