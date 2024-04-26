package com.vukasinprvulovic.application.features.cryptomarketplace.result

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

data class CryptoMarketplaceResults(
    private var dataSet: Set<Data> = emptySet(),
    private var error: List<Error> = emptyList()
) {
    private val dataMutex = Mutex()
    private val errorMutex = Mutex()

    suspend fun data(): Set<Data> = dataMutex.withLock {
        dataSet
    }

    suspend fun errors(): List<Error> = errorMutex.withLock {
        error
    }

    interface Data

    interface FinalData: Data

    open class Error(cause: Throwable?): Throwable(cause)

    internal suspend fun addData(data: Data) = dataMutex.withLock {
        this.dataSet += data
    }

    internal suspend fun addError(error: Error) = errorMutex.withLock {
        this.error += error
    }
}

inline fun <reified SpecificData> Set<CryptoMarketplaceResults.Data>.findDataOfInstance(): SpecificData? {
    return filterIsInstance<SpecificData>().firstOrNull()
}

inline fun <reified SpecificData> Set<CryptoMarketplaceResults.Data>.requireDataOfInstance(): SpecificData  {
    return findDataOfInstance() ?: error("Data(class = ${SpecificData::class.simpleName}) not found")
}
