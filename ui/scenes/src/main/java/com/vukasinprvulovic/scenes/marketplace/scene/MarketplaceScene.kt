package com.vukasinprvulovic.scenes.marketplace.scene

import com.vukasinprvulovic.scenes.marketplace.scene.models.TradingPairModel

data class MarketplaceViewState(
    val isLoading: Boolean = true,
    val pairs: List<TradingPairModel>
)
