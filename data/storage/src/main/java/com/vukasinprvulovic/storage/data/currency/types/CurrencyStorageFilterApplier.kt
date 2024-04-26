package com.vukasinprvulovic.storage.data.currency.types

import com.vukasinprvulovic.application.entities.currency.Currency

interface CurrencyStorageFilterApplier<RetrievedCurrency : Currency<*>> {
    val retrievedClass: Class<RetrievedCurrency>

    suspend fun getFilteredCurrencies(): Result<List<RetrievedCurrency>>
}

class CurrencyStorageFilterAppliers(
    val set: Set<CurrencyStorageFilterApplier<*>>
)
