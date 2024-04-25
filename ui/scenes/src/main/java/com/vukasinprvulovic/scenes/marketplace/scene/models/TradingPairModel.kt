package com.vukasinprvulovic.scenes.marketplace.scene.models

data class TradingPairModel(
    val baseCurrencyName: String,
    val baseCurrencyLogo: String,
    val tradingPairTicker: String,
    val price: String,
    val quoteCurrencySymbol: String,
    val priceChange: String,
    val isPriceIncrease: Boolean
) {
    val formattedPrice = "$quoteCurrencySymbol$price"
    val formattedPriceChange = "${if(isPriceIncrease)"+" else ""}$priceChange%"
}
