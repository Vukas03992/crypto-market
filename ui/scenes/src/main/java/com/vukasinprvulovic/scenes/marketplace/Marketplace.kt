package com.vukasinprvulovic.scenes.marketplace

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vukasinprvulovic.design.colors.palettes.CryptoMarketplacePalette
import com.vukasinprvulovic.elements.features.marketplace.currency.MarketplaceCurrencyIcon
import com.vukasinprvulovic.elements.features.marketplace.tradingpairs.TradingPair
import com.vukasinprvulovic.elements.features.marketplace.tradingpairs.TradingPairPrice
import com.vukasinprvulovic.elements.utils.lifecycle.LifecycleEvents
import com.vukasinprvulovic.scenes.R
import com.vukasinprvulovic.scenes.marketplace.scene.MarketplaceViewState
import com.vukasinprvulovic.scenes.marketplace.scene.models.TradingPairModel

@Composable
fun Marketplace(
    viewModel: MarketplaceViewModel = hiltViewModel()
) {
    val viewState by viewModel.marketplaceState.collectAsStateWithLifecycle()

    LifecycleEvents(lifecycleOwner = LocalLifecycleOwner.current,
        onResume = { viewModel.onResume() },
        onPause = { viewModel.onPause() })

    Surface(modifier = Modifier.background(Color.White)) {
        MarketplaceContent(viewState)
    }
}

@Composable
private fun MarketplaceContent(
    viewState: MarketplaceViewState
) {
    Scaffold(
        topBar = {
            MarketplaceAppBar(
                viewState.isLoading
            )
        },
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            items(viewState.pairs.size) { index ->
                val tradingPair = viewState.pairs[index]
                MarketplaceTradingPairItem(tradingPair)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MarketplaceAppBar(
    isLoading: Boolean,
    modifier: Modifier = Modifier,
) {
    Box {
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = modifier
                .fillMaxWidth()
                .background(Color.Transparent)
                .padding(16.dp)
        ) {
            SearchBar(
                query = "",
                onQueryChange = {},
                placeholder = {
                    Text(stringResource(id = R.string.search_crypto))
                },
                onSearch = {},
                active = false,
                onActiveChange = {},
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) { }
        }
        if (isLoading) {
            LinearProgressIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            )
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
