package com.vukasinprvulovic.application.data.sources.storage.currency

import com.vukasinprvulovic.application.data.sources.storage.currency.filters.CurrencyStorageFilter
import com.vukasinprvulovic.application.entities.currency.Currency

interface CurrencyStorage {
    suspend fun <RetrievedCurrency: Currency<*>> retrieveCurrencies(filter: CurrencyStorageFilter<RetrievedCurrency>): Result<List<RetrievedCurrency>>
}
