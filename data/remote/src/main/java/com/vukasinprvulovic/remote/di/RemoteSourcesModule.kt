package com.vukasinprvulovic.remote.di

import com.vukasinprvulovic.application.data.sources.remote.trading.pairs.TradingPairsRemoteSource
import com.vukasinprvulovic.remote.sources.trading.pairs.TradingPairsRemoteSystem
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteSourcesModule {

    @Provides
    @Singleton
    fun provideTradingPairRemoteSource(tradingPairsRemoteSystem: TradingPairsRemoteSystem): TradingPairsRemoteSource = tradingPairsRemoteSystem
}
