@file:Suppress("UNCHECKED_CAST")

package com.vukasinprvulovic.storage.data.currency

import com.vukasinprvulovic.application.data.sources.storage.currency.CurrencyStorage
import com.vukasinprvulovic.application.data.sources.storage.currency.filters.CurrencyStorageFilter
import com.vukasinprvulovic.application.entities.currency.Currency
import com.vukasinprvulovic.configuration.di.annotations.ApplicationCoroutineDispatcher
import com.vukasinprvulovic.storage.data.currency.types.CurrencyStorageFilterAppliers
import com.vukasinprvulovic.utils.kotlin.results.foldResultsSuspend
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyStorageImplementation @Inject constructor(
    @ApplicationCoroutineDispatcher private val dispatcher: CoroutineDispatcher,
    private val currencyStorageFilterAppliers: CurrencyStorageFilterAppliers
): CurrencyStorage {
    override suspend fun <RetrievedCurrency : Currency<*>> retrieveCurrencies(filter: CurrencyStorageFilter<RetrievedCurrency>): Result<List<RetrievedCurrency>> = withContext(dispatcher) {
        foldResultsSuspend {
            val applier = currencyStorageFilterAppliers.set.firstOrNull { it.retrievedClass == filter.filteringClass } ?: error("No applier found for ${filter.filteringClass.simpleName}")
            val currencies = (applier.getFilteredCurrencies().getOrThrow() as? List<RetrievedCurrency>) ?: error("Invalid currencies type")
            Result.success(currencies)
        }
    }
}
