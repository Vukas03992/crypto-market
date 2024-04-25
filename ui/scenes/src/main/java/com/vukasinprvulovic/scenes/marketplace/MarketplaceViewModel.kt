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
import com.vukasinprvulovic.scenes.marketplace.scene.MarketplaceViewState
import com.vukasinprvulovic.scenes.marketplace.scene.models.TradingPairModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketplaceViewModel @Inject constructor(
    private val cryptoMarketplace: CryptoMarketplace
): ViewModel() {
    private val internalMarketplaceState = MutableStateFlow(MarketplaceViewState(pairs = emptyList()))
    val marketplaceState: StateFlow<MarketplaceViewState>
        get() = internalMarketplaceState

    init {
        cryptoMarketplace.subscribe(viewModelScope) {
           viewModelScope.launch {
               e("TAG", it.toString())
               val data = it.data()
               val isFinalDataReceived = data.findDataOfInstance<CryptoMarketplaceResults.FinalData>()
               val isLoading = isFinalDataReceived == null
               val tradingPairs = data.findDataOfInstance<TradingPairs>()

               if (tradingPairs == null) {
                   internalMarketplaceState.value = MarketplaceViewState(isLoading = isLoading, pairs = internalMarketplaceState.value.pairs)
               } else {
                   internalMarketplaceState.value = MarketplaceViewState(
                       isLoading = false,
                       tradingPairs.map {
                           val dailyChanges = it.tradingData.getData<Price.Change.Daily>()
                           TradingPairModel(
                               it.baseCurrency.properties.name,
                               it.baseCurrency.properties.icon ?: "",
                               it.ticker.symbol,
                               it.tradingData.price().toString(),
                               it.quoteCurrency.properties.symbol,
                               dailyChanges.percentageChangedSinceYesterday.toString(),
                               dailyChanges.percentageChangedSinceYesterday >= 0
                           )
                       }
                   )
               }
           }
        }
        viewModelScope.launch { cryptoMarketplace.resume() }
    }

    fun onResume() {
        viewModelScope.launch { cryptoMarketplace.resume() }
    }

    fun onPause() {
        viewModelScope.launch { cryptoMarketplace.pause() }
    }
}
