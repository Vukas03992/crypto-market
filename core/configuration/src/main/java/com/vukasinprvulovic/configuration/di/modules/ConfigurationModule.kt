package com.vukasinprvulovic.configuration.di.modules

import com.vukasinprvulovic.configuration.di.annotations.BitfinexBaseUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ConfigurationModule {

    @Provides
    @BitfinexBaseUrl
    fun provideBitfinexBaseUrl(): String {
        return "https://api-pub.bitfinex.com/v2"
    }
}
