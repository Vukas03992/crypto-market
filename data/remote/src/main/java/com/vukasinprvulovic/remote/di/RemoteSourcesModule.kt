package com.vukasinprvulovic.remote.di

import com.vukasinprvulovic.application.data.sources.remote.trading.pairs.TradingPairsRemoteSource
import com.vukasinprvulovic.remote.sources.trading.pairs.TradingPairsRemoteSystem
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RemoteSourcesModule {

    @Provides
    fun provideTradingPairRemoteSource(tradingPairsRemoteSystem: TradingPairsRemoteSystem): TradingPairsRemoteSource = tradingPairsRemoteSystem
}
