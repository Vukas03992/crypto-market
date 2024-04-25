package com.vukasinprvulovic.device.di

import com.vukasinprvulovic.application.components.device.connectivity.internet.InternetConnectionStatus
import com.vukasinprvulovic.device.internet.InternetConnection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DeviceModule {

    @Provides
    fun provideInternetConnection(internetConnection: InternetConnection): InternetConnectionStatus {
        return internetConnection
    }
}
