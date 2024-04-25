package com.vukasinprvulovic.scenes.marketplace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vukasinprvulovic.application.features.cryptomarketplace.CryptoMarketplace
import com.vukasinprvulovic.scenes.marketplace.scene.MarketplaceViewEvent
import com.vukasinprvulovic.scenes.marketplace.scene.MarketplaceViewState
import com.vukasinprvulovic.scenes.marketplace.scene.state.produceMarketplaceViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
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
            internalMarketplaceState.value = produceMarketplaceViewState(internalMarketplaceState.value, it)
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
}
