package com.vukasinprvulovic.application.entities.trading.data

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import java.util.Collections

interface TradingData

interface Trading {

    data class Data(
        private var dataList: List<TradingData> = emptyList()
    ) {
        val list: List<TradingData>
            get() = Collections.unmodifiableList(dataList)
        private val mutex = Mutex()

        suspend fun addData(data: TradingData) = mutex.withLock {
            dataList += data
        }

        suspend fun addData(vararg data: TradingData) = mutex.withLock {
            dataList += data
        }

        suspend fun addAll(dataList: List<TradingData>) = mutex.withLock {
            this.dataList += dataList
        }

        suspend fun removeData(data: TradingData) = mutex.withLock {
            dataList -= data
        }

        suspend fun clear() = mutex.withLock {
            dataList = emptyList()
        }

        inline fun <reified Data : TradingData> getAllData(): List<Data> {
            return list.filterIsInstance<Data>()
        }

        inline fun <reified Data : TradingData> getData(): Data {
            return getAllData<Data>().firstOrNull() ?: error("Data(class = ${Data::class.simpleName}) not found")
        }
    }
}
