package com.vukasinprvulovic.network.di

import com.vukasinprvulovic.network.api.tickers.KtorTickersAPI
import com.vukasinprvulovic.network.api.tickers.TickersAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideTickersAPI(ktorTickersAPI: KtorTickersAPI): TickersAPI = ktorTickersAPI
}
