package com.vukasinprvulovic.scenes.marketplace

import android.util.Log.e
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vukasinprvulovic.application.entities.trading.data.price.Price
import com.vukasinprvulovic.application.entities.trading.data.price.price
import com.vukasinprvulovic.application.entities.trading.pair.TradingPairs
import com.vukasinprvulovic.application.features.cryptomarketplace.CryptoMarketplace
import com.vukasinprvulovic.application.features.cryptomarketplace.result.CryptoMarketplaceResults
import com.vukasinprvulovic.application.features.cryptomarketplace.result.findDataOfInstance
import com.vukasinprvulovic.application.features.cryptomarketplace.store.components.SearchedTradingPairs
import com.vukasinprvulovic.scenes.marketplace.scene.MarketplaceViewEvent
import com.vukasinprvulovic.scenes.marketplace.scene.MarketplaceViewState
import com.vukasinprvulovic.scenes.marketplace.scene.models.TradingPairModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class MarketplaceViewModel @Inject constructor(
    private val cryptoMarketplace: CryptoMarketplace
): ViewModel() {
    private val internalMarketplaceState = MutableStateFlow(MarketplaceViewState(pairs = emptyList(), onMarketplaceViewEvent = ::handleMarketplaceEvent))
    val marketplaceState: StateFlow<MarketplaceViewState>
        get() = internalMarketplaceState

    init {
        cryptoMarketplace.subscribe(viewModelScope) {
            e("TAG", it.toString())
            val data = it.data()
            val errors = it.errors()
            val isFinalDataReceived = data.findDataOfInstance<CryptoMarketplaceResults.FinalData>()
            val isLoading = isFinalDataReceived == null && errors.isEmpty()
            val tradingPairs = data.findDataOfInstance<TradingPairs>()
            val searchedTradingPairs = data.findDataOfInstance<SearchedTradingPairs>()

            if (tradingPairs == null && searchedTradingPairs == null) {
                internalMarketplaceState.value = internalMarketplaceState.value.copy(isLoading = isLoading, error = errors)
            } else {
                val pairs = searchedTradingPairs?.results ?: tradingPairs
                internalMarketplaceState.value = internalMarketplaceState.value.copy(
                    isLoading = isLoading,
                    searchingToken = searchedTradingPairs?.searchingToken ?: "",
                    pairs = pairs?.map { tradingPair ->
                        val dailyChanges = tradingPair.tradingData.getData<Price.Change.Daily>()
                        TradingPairModel(
                            searchedTradingPairs?.searchingToken ?: "",
                            tradingPair.baseCurrency.properties.name,
                            tradingPair.baseCurrency.properties.icon ?: "",
                            tradingPair.ticker.symbol,
                            tradingPair.tradingData.price().let {
                                if (it == 0f) "" else formatToMaxFourDecimals(it)
                            },
                            tradingPair.quoteCurrency.properties.symbol,
                            (dailyChanges.percentageChangedSinceYesterday * 100f).let {
                                if (it == 0f) "" else formatToMaxFourDecimals(it)
                            },
                            dailyChanges.percentageChangedSinceYesterday >= 0
                        )
                    } ?: internalMarketplaceState.value.pairs,
                    error = it.errors()
                )
            }
        }
    }

    private fun handleMarketplaceEvent(event: MarketplaceViewEvent) {
        when (event) {
            is MarketplaceViewEvent.OnResume -> viewModelScope.launch { cryptoMarketplace.resume() }
            is MarketplaceViewEvent.OnPause -> viewModelScope.launch { cryptoMarketplace.pause() }
            is MarketplaceViewEvent.Search -> search(event.query)
        }
    }

    private fun search(token: String) {
        viewModelScope.launch {
            internalMarketplaceState.value = internalMarketplaceState.value.copy(isLoading = true)
            cryptoMarketplace.search(token)
        }
    }

    private fun formatToMaxFourDecimals(number: Float): String {
        val bigDecimal = BigDecimal(number.toDouble())
        val scaled = bigDecimal.setScale(4, RoundingMode.HALF_UP)
        val formattedString = scaled.toPlainString()
        val decimalPointIndex = formattedString.indexOf('.')
        if (decimalPointIndex != -1) {
            return formattedString.trimEnd('0').trimEnd('.')
        }
        return formattedString
    }
}
