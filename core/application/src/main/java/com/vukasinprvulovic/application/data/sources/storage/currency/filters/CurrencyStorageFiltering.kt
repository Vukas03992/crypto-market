package com.vukasinprvulovic.application.data.sources.storage.currency.filters

import com.vukasinprvulovic.application.entities.currency.CryptoCurrency
import com.vukasinprvulovic.application.entities.currency.Currency

interface CurrencyStorageFilter<FilteredCurrency: Currency<*>> {
    val filteringClass: Class<FilteredCurrency>
}

class CryptoCurrencyStorageFilter: CurrencyStorageFilter<CryptoCurrency> {
    override val filteringClass: Class<CryptoCurrency> = CryptoCurrency::class.java
}
