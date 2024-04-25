package com.vukasinprvulovic.elements.features.marketplace.tradingpairs

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.vukasinprvulovic.design.types.poppins

@Composable
fun TradingPair(
    baseCurrencyName: AnnotatedString,
    tradingPairTicker: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Text(text = baseCurrencyName, style = MaterialTheme.typography.titleLarge, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Text(text = tradingPairTicker, style = MaterialTheme.typography.labelLarge)
    }
}

@Preview(showBackground = true)
@Composable
private fun TradingPairPreview() {
    TradingPair(
        buildAnnotatedString { append("BTC") },"BTC/USD"
    )
}
