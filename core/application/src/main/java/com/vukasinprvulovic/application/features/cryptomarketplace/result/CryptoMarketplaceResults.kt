package com.vukasinprvulovic.application.features.cryptomarketplace.result

import com.vukasinprvulovic.application.entities.trading.data.Trading
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

data class CryptoMarketplaceResults(
    private var dataList: List<Data> = emptyList(),
    private var error: List<Error> = emptyList()
) {
    private val dataMutex = Mutex()
    private val errorMutex = Mutex()

    suspend fun data(): List<Data> = dataMutex.withLock {
        dataList
    }

    suspend fun errors(): List<Error> = errorMutex.withLock {
        error
    }

    interface Data

    open class Error(cause: Throwable?): Throwable(cause)

    internal suspend fun addData(data: Data) = dataMutex.withLock {
        this.dataList += data
    }

    internal suspend fun addError(error: Error) = errorMutex.withLock {
        this.error += error
    }
}

inline fun <reified SpecificData> List<CryptoMarketplaceResults.Data>.getDataOfInstance(): SpecificData  {
    return filterIsInstance<SpecificData>().firstOrNull() ?: error("Data(class = ${SpecificData::class.simpleName}) not found")
}
