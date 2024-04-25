package com.vukasinprvulovic.scenes.marketplace.scene

import com.vukasinprvulovic.scenes.marketplace.scene.models.TradingPairModel

data class MarketplaceViewState(
    val isLoading: Boolean = true,
    val pairs: List<TradingPairModel>,
    val onMarketplaceViewEvent: (MarketplaceViewEvent) -> Unit = {}
)

sealed class MarketplaceViewEvent {
    data object OnResume : MarketplaceViewEvent()
    data object OnPause : MarketplaceViewEvent()
    data class Search(val query: String) : MarketplaceViewEvent()
}
