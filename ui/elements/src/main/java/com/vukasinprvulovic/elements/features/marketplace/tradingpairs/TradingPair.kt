package com.vukasinprvulovic.elements.features.marketplace.tradingpairs

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.vukasinprvulovic.design.types.poppins

@Composable
fun TradingPair(
    baseCurrencyName: String,
    tradingPairTicker: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Text(text = baseCurrencyName, style = MaterialTheme.typography.titleLarge, fontFamily = poppins)
        Text(text = tradingPairTicker, style = MaterialTheme.typography.bodyMedium, fontFamily = poppins)
    }
}

@Preview(showBackground = true)
@Composable
private fun TradingPairPreview() {
    TradingPair(
        "BTC","BTC/USD"
    )
}
