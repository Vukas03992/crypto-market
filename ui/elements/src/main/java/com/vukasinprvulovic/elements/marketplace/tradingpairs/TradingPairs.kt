package com.vukasinprvulovic.elements.marketplace.tradingpairs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vukasinprvulovic.design.colors.palettes.CryptoMarketplacePalette
import com.vukasinprvulovic.design.types.poppins

@Composable
fun TradingPair(
    modifier: Modifier = Modifier,
    ticker: String = "BTC/USD",
    price: String = "1.0000",
) {
    Box(modifier = modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp, horizontal = 16.dp)) {
        Surface(
            shape = MaterialTheme.shapes.large,
            color = CryptoMarketplacePalette.gray,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
            ) {
                Text(text = ticker, fontFamily = poppins, modifier = Modifier.padding(16.dp))
                Text(text = price, fontFamily = poppins, modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Preview(backgroundColor = 0xFFFFFFFF)
@Composable
private fun TradingPairPreview() {
    TradingPair()
}
