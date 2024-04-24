package com.vukasinprvulovic.storage.di

import com.vukasinprvulovic.storage.data.currency.types.Crypto
import com.vukasinprvulovic.storage.data.currency.types.CurrencyStorageFilterApplier
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CurrencyStorageModule {

    @Provides
    @IntoSet
    @Singleton
    fun provideCryptoCurrencyStorage(crypto: Crypto): CurrencyStorageFilterApplier<*> = crypto
}
