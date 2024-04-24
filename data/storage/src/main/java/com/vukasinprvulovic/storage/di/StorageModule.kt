package com.vukasinprvulovic.storage.di

import com.vukasinprvulovic.application.data.sources.storage.currency.CurrencyStorage
import com.vukasinprvulovic.storage.data.currency.CurrencyStorageImplementation
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StorageModule {

    @Singleton
    @Binds
    abstract fun bindCurrencyStorage(currencyStorageImplementation: CurrencyStorageImplementation) : CurrencyStorage
}
