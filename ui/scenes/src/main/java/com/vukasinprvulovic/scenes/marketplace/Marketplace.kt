package com.vukasinprvulovic.scenes.marketplace

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vukasinprvulovic.design.colors.palettes.CryptoMarketplacePalette
import com.vukasinprvulovic.elements.features.marketplace.currency.MarketplaceCurrencyIcon
import com.vukasinprvulovic.elements.features.marketplace.tradingpairs.TradingPair
import com.vukasinprvulovic.elements.features.marketplace.tradingpairs.TradingPairPrice
import com.vukasinprvulovic.scenes.marketplace.scene.MarketplaceViewState
import com.vukasinprvulovic.scenes.marketplace.scene.models.TradingPairModel

@Composable
fun Marketplace(
    viewModel: MarketplaceViewModel = hiltViewModel()
) {
    val viewState by viewModel.marketplaceState.collectAsStateWithLifecycle()
    Surface(modifier = Modifier.background(Color.White)) {
        MarketplaceContent(viewState)
    }
}

@Composable
private fun MarketplaceContent(
    viewState: MarketplaceViewState,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(viewState.pairs.size) { index ->
            val tradingPair = viewState.pairs[index]
            MarketplaceTradingPairItem(tradingPair)
        }
    }
}

@Composable
private fun MarketplaceTradingPairItem(
    tradingPairModel: TradingPairModel,
    modifier: Modifier = Modifier
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
                horizontalArrangement = Arrangement.Start,
                modifier = modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
            ) {
                MarketplaceCurrencyIcon(currencyName = tradingPairModel.baseCurrencyName, currencyLogoUrl = tradingPairModel.baseCurrencyLogo)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    TradingPair(
                        baseCurrencyName = tradingPairModel.baseCurrencyName,
                        tradingPairTicker = tradingPairModel.tradingPairTicker
                    )
                    TradingPairPrice(
                        marketPrice = tradingPairModel.formattedPrice,
                        priceChange = tradingPairModel.formattedPriceChange,
                        modifier = Modifier.fillMaxHeight()
                    )
                }
            }
        }
    }
}

/*
@Preview(showBackground = true)
@Composable
fun MarketplaceContentPreview() {
    MarketplaceContent(
        viewState = MarketplaceViewState(
            listOf(
                TradingPairModel("","","BTC/USD", "10000"),
                TradingPairModel("","","ETH/USD", "1000")
            )
        )
    )
}
*/
