package com.vukasinprvulovic.scenes.marketplace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vukasinprvulovic.application.entities.trading.data.price.Price
import com.vukasinprvulovic.application.entities.trading.pair.TradingPairs
import com.vukasinprvulovic.application.features.cryptomarketplace.CryptoMarketplace
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
    private val internalMarketplaceState = MutableStateFlow(MarketplaceViewState(emptyList()))
    val marketplaceState: StateFlow<MarketplaceViewState>
        get() = internalMarketplaceState

    init {
        cryptoMarketplace.subscribe(viewModelScope) {
            it.data().findDataOfInstance<TradingPairs>()?.let { tradingPairs ->
                internalMarketplaceState.value = MarketplaceViewState(
                    tradingPairs.map {
                        TradingPairModel(
                            it.ticker.symbol,
                            it.tradingData.getData<Price.LastTrade>().amount.toString()
                        )
                    }
                )
            }
        }
        viewModelScope.launch { cryptoMarketplace.resume() }
    }
}
