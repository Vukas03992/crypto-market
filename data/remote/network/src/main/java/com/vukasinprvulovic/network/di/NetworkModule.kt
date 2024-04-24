package com.vukasinprvulovic.network.di

import com.vukasinprvulovic.network.api.tickers.KtorTickersAPI
import com.vukasinprvulovic.network.api.tickers.TickersAPI
import com.vukasinprvulovic.network.integrations.ktor.defaultHttpClientConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.android.Android
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideTickersAPI(ktorTickersAPI: KtorTickersAPI): TickersAPI = ktorTickersAPI

    @Provides
    fun provideKtorHttpEngine(): HttpClientEngine = HttpClient(Android).engine

    @Provides
    fun provideKtorHttpConfig(): HttpClientConfig<out HttpClientEngineConfig> = defaultHttpClientConfig
}
