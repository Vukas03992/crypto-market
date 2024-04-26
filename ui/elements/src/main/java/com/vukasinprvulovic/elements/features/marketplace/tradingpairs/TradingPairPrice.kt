package com.vukasinprvulovic.elements.features.marketplace.tradingpairs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.vukasinprvulovic.design.types.poppins

@Composable
fun TradingPairPrice(
    marketPrice: String,
    priceChange: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(text = marketPrice, style = MaterialTheme.typography.titleMedium, textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth(), maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(text = priceChange, style = MaterialTheme.typography.labelSmall, textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())
    }
}

@Preview(showBackground = true)
@Composable
private fun TradingPairPricePreview() {
    TradingPairPrice(
        "0.123456","-0.123456"
    )
}