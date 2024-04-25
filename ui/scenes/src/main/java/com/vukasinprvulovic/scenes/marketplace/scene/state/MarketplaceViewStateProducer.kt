package com.vukasinprvulovic.scenes.marketplace.scene.state

import com.vukasinprvulovic.application.entities.trading.data.price.Price
import com.vukasinprvulovic.application.entities.trading.data.price.price
import com.vukasinprvulovic.application.entities.trading.pair.TradingPairs
import com.vukasinprvulovic.application.features.cryptomarketplace.result.CryptoMarketplaceResults
import com.vukasinprvulovic.application.features.cryptomarketplace.result.findDataOfInstance
import com.vukasinprvulovic.application.features.cryptomarketplace.store.components.SearchedTradingPairs
import com.vukasinprvulovic.scenes.marketplace.scene.MarketplaceViewState
import com.vukasinprvulovic.scenes.marketplace.scene.models.TradingPairModel
import java.math.BigDecimal
import java.math.RoundingMode

suspend fun produceMarketplaceViewState(currentState: MarketplaceViewState, marketplaceResults: CryptoMarketplaceResults): MarketplaceViewState {
    val resultData = marketplaceResults.data()
    val errors = marketplaceResults.errors()
    val isFinalDataReceived = resultData.findDataOfInstance<CryptoMarketplaceResults.FinalData>()
    val isLoading = isFinalDataReceived == null && errors.isEmpty()

    return parseData(resultData, errors, isLoading, currentState)
}

fun parseData(resultData: Set<CryptoMarketplaceResults.Data>, errors: List<CryptoMarketplaceResults.Error>, loading: Boolean, currentState: MarketplaceViewState): MarketplaceViewState {
    val tradingPairs = resultData.findDataOfInstance<TradingPairs>()
    val searchedTradingPairs = resultData.findDataOfInstance<SearchedTradingPairs>()

    val noData = tradingPairs == null && searchedTradingPairs == null

    return if (noData) {
        currentState.copy(isLoading = loading, error = errors)
    } else {
        val pairs = searchedTradingPairs?.results ?: tradingPairs
        val searchingToken = searchedTradingPairs?.searchingToken ?: ""
        currentState.copy(
            isLoading = loading,
            searchingToken = searchingToken,
            pairs = parseTradingPairs(pairs, searchingToken),
            error = errors
        )
    }
}

fun parseTradingPairs(tradingPairs: TradingPairs?, searchToken: String): List<TradingPairModel> {

    fun formatToMaxFourDecimals(number: Float): String {
        val bigDecimal = BigDecimal(number.toDouble())
        val scaled = bigDecimal.setScale(4, RoundingMode.HALF_UP)
        val formattedString = scaled.toPlainString()
        val decimalPointIndex = formattedString.indexOf('.')
        if (decimalPointIndex != -1) {
            return formattedString.trimEnd('0').trimEnd('.')
        }
        return formattedString
    }

    return tradingPairs?.map { tradingPair ->
        val dailyChanges = tradingPair.tradingData.getData<Price.Change.Daily>()
        val price = tradingPair.tradingData.price()
        val formattedPrice = if (price == 0f) "" else formatToMaxFourDecimals(price)
        val priceChange = dailyChanges.percentageChangedSinceYesterday * 100f
        val formattedPriceChange = if (priceChange == 0f) "" else formatToMaxFourDecimals(priceChange)

        TradingPairModel(
            searchingToken = searchToken,
            baseCurrencyName = tradingPair.baseCurrency.properties.name,
            baseCurrencyLogo = tradingPair.baseCurrency.properties.icon ?: "",
            tradingPairTicker = tradingPair.ticker.symbol,
            price = formattedPrice,
            quoteCurrencySymbol = tradingPair.quoteCurrency.properties.symbol,
            priceChange = formattedPriceChange,
            isPriceIncrease = dailyChanges.percentageChangedSinceYesterday >= 0
        )
    } ?: emptyList()
}
