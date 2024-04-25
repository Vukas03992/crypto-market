package com.vukasinprvulovic.scenes.marketplace

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vukasinprvulovic.design.colors.palettes.CryptoMarketplacePalette
import com.vukasinprvulovic.design.types.poppins
import com.vukasinprvulovic.elements.features.marketplace.currency.MarketplaceCurrencyIcon
import com.vukasinprvulovic.elements.features.marketplace.tradingpairs.TradingPair
import com.vukasinprvulovic.elements.features.marketplace.tradingpairs.TradingPairPrice
import com.vukasinprvulovic.elements.utils.lifecycle.LifecycleEvents
import com.vukasinprvulovic.scenes.R
import com.vukasinprvulovic.scenes.marketplace.scene.MarketplaceViewEvent
import com.vukasinprvulovic.scenes.marketplace.scene.MarketplaceViewState
import com.vukasinprvulovic.scenes.marketplace.scene.models.TradingPairModel

@Composable
fun Marketplace(
    viewModel: MarketplaceViewModel = hiltViewModel()
) {
    val viewState by viewModel.marketplaceState.collectAsStateWithLifecycle()

    LifecycleEvents(lifecycleOwner = LocalLifecycleOwner.current,
        onResume = { viewState.onMarketplaceViewEvent(MarketplaceViewEvent.OnResume) },
        onPause = { viewState.onMarketplaceViewEvent(MarketplaceViewEvent.OnPause) })

    Surface(modifier = Modifier.background(Color.White)) {
        MarketplaceContent(viewState)
    }
}

@Composable
private fun MarketplaceContent(
    viewState: MarketplaceViewState
) {
    val listState = rememberLazyListState()
    val isScrolled = remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 0
        }
    }
    val elevation by animateDpAsState(
        targetValue = if (isScrolled.value) 8.dp else 0.dp, label = ""
    )

    Scaffold(
        topBar = {
            MarketplaceAppBar(
                viewState.isLoading,
                elevation,
                onSearchQueryChange = { viewState.onMarketplaceViewEvent(MarketplaceViewEvent.Search(it)) }
            )
        },
    ) { paddingValues ->
        LazyColumn(
            state = listState,
            modifier = Modifier.padding(top = paddingValues.calculateTopPadding() - 8.dp)
        ) {
            items(viewState.pairs.size,
                key = { viewState.pairs[it].tradingPairTicker }) { index ->
                val tradingPair = viewState.pairs[index]
                val paddingTop = if (index == 0) 24.dp else 8.dp
                MarketplaceTradingPairItem(tradingPair, modifier = Modifier.padding(top = paddingTop))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MarketplaceAppBar(
    isLoading: Boolean,
    elevation: Dp,
    modifier: Modifier = Modifier,
    onSearchQueryChange: (String) -> Unit = { },
) {
    val searchingToken = remember { mutableStateOf("") }

    Surface(
        shadowElevation = elevation,
        shape = RoundedCornerShape(0.dp, 0.dp, 32.dp, 32.dp),
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.statusBars)
        ) {

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                ) {

                    Image(
                        painter = painterResource(id = com.vukasinprvulovic.design.R.drawable.logo),
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .padding(end = 16.dp, start = 4.dp, top = 2.dp)
                    )

                    Text(text = "Crypto Marketplace", style = MaterialTheme.typography.titleLarge, fontFamily = poppins)
                }

                SearchBar(
                    query = searchingToken.value,
                    onQueryChange = {
                        searchingToken.value = it
                        onSearchQueryChange(it)
                    },
                    placeholder = {
                        Text(stringResource(id = R.string.search_crypto))
                    },
                    onSearch = {
                        onSearchQueryChange(it)
                    },
                    active = false,
                    onActiveChange = {},
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )
                    },
                    colors = SearchBarDefaults.colors(
                        containerColor = Color.White,
                        inputFieldColors = SearchBarDefaults.inputFieldColors(
                            focusedTextColor = CryptoMarketplacePalette.black,
                            cursorColor = CryptoMarketplacePalette.black,
                            focusedLeadingIconColor = CryptoMarketplacePalette.black,
                        )
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp)
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
}

@Composable
private fun MarketplaceTradingPairItem(
    tradingPairModel: TradingPairModel,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 8.dp)
    ) {
        Surface(
            shape = MaterialTheme.shapes.large,
            color = CryptoMarketplacePalette.gray,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
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

@Preview(showBackground = true)
@Composable
fun MarketplaceAppBarPreview() {
    MarketplaceAppBar(isLoading = false, 0.dp)
}
