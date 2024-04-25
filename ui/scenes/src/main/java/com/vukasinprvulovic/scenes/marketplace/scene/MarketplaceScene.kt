package com.vukasinprvulovic.scenes.marketplace.scene

import com.vukasinprvulovic.application.features.cryptomarketplace.result.CryptoMarketplaceResults
import com.vukasinprvulovic.scenes.marketplace.scene.models.TradingPairModel

data class MarketplaceViewState(
    val isLoading: Boolean = true,
    val searchingToken: String = "",
    val pairs: List<TradingPairModel>,
    val error: List<CryptoMarketplaceResults.Error> = emptyList(),
    val onMarketplaceViewEvent: (MarketplaceViewEvent) -> Unit = {}
) {
    val isSearchingInProgress = searchingToken.isNotBlank()
    val noResults: Boolean = pairs.isEmpty()
    val noSearchingResults: Boolean = noResults && isSearchingInProgress
    val isError = error.isNotEmpty()
}

sealed class MarketplaceViewEvent {
    data object OnResume : MarketplaceViewEvent()
    data object OnPause : MarketplaceViewEvent()
    data class Search(val query: String) : MarketplaceViewEvent()
}
