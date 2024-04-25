package com.vukasinprvulovic.elements.features.marketplace.tradingpairs

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TradingPair(
    modifier: Modifier = Modifier,
    baseCurrencyName: String = "Bitcoin",
    tradingPairTicker: String = "BIT/USD",
) {
    Column(
        modifier = modifier
    ) {
        Text(text = baseCurrencyName, style = MaterialTheme.typography.titleLarge)
        Text(text = tradingPairTicker, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview
@Composable
private fun TradingPairPreview() {
    TradingPair()
}
