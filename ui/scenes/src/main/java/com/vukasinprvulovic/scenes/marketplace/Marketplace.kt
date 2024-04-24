package com.vukasinprvulovic.scenes.marketplace

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vukasinprvulovic.elements.marketplace.tradingpairs.TradingPair
import com.vukasinprvulovic.scenes.marketplace.scene.MarketplaceViewState
import com.vukasinprvulovic.scenes.marketplace.scene.models.TradingPairModel

@Composable
fun Marketplace(
    viewModel: MarketplaceViewModel = hiltViewModel()
) {
    val viewState by viewModel.marketplaceState.collectAsStateWithLifecycle()
    Surface {
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
            TradingPair(
                ticker = tradingPair.ticker,
                price = tradingPair.price
            )
        }
    }
}

@Preview
@Composable
fun MarketplaceContentPreview() {
    MarketplaceContent(
        viewState = MarketplaceViewState(
            listOf(
                TradingPairModel("BTC/USD", "10000"),
                TradingPairModel("ETH/USD", "1000")
            )
        )
    )
}
