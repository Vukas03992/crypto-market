package com.vukasinprvulovic.configuration.di.modules

import com.google.common.util.concurrent.ThreadFactoryBuilder
import com.vukasinprvulovic.configuration.di.annotations.ApplicationCoroutineDispatcher
import com.vukasinprvulovic.configuration.di.annotations.ApplicationCoroutineScope
import com.vukasinprvulovic.configuration.di.annotations.BitfinexBaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ConfigurationModule {

    @Provides
    @BitfinexBaseUrl
    fun provideBitfinexBaseUrl(): String {
        return "https://api-pub.bitfinex.com/v2"
    }

    @Singleton
    @Provides
    @ApplicationCoroutineDispatcher
    fun provideDefaultDispatcher(): CoroutineDispatcher = Executors.newFixedThreadPool(10, ThreadFactoryBuilder().setNameFormat("crypto-market-worker-%d").build()).asCoroutineDispatcher()

    @Singleton
    @Provides
    @ApplicationCoroutineScope
    fun provideApplicationCoroutineScope(@ApplicationCoroutineDispatcher dispatcher: CoroutineDispatcher): CoroutineScope = CoroutineScope(SupervisorJob() + dispatcher)
}
