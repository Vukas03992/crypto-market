package com.vukasinprvulovic.scenes.marketplace.scene.models

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.vukasinprvulovic.design.colors.palettes.CryptoMarketplacePalette

data class TradingPairModel(
    val searchingToken: String,
    val baseCurrencyName: String,
    val baseCurrencyLogo: String,
    val tradingPairTicker: String,
    val price: String,
    val quoteCurrencySymbol: String,
    val priceChange: String,
    val isPriceIncrease: Boolean
) {
    val formattedPrice = if (price != "") "$quoteCurrencySymbol$price" else "No data"
    val formattedPriceChange = if (priceChange != "") "${if(isPriceIncrease)"+" else ""}$priceChange%" else ""

    val annotatedBaseCurrencyName = buildAnnotatedString {
        if (searchingToken.isNotEmpty()) {
            val searchingLetters = searchingToken.lowercase().toSet()
            baseCurrencyName.forEach {
                if (it.lowercaseChar() in searchingLetters) {
                    withStyle(style = SpanStyle(color = CryptoMarketplacePalette.blue)) {
                        append(it)
                    }
                } else {
                    append(it)
                }
            }
        } else {
            append(baseCurrencyName)
        }
    }
}
