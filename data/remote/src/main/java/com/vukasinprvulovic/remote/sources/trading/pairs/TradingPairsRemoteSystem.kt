package com.vukasinprvulovic.remote.sources.trading.pairs

import androidx.annotation.VisibleForTesting
import com.vukasinprvulovic.application.data.sources.remote.trading.pairs.TradingPairsRemoteSource
import com.vukasinprvulovic.application.entities.trading.data.price.Price
import com.vukasinprvulovic.application.entities.trading.data.price.ask.ASK
import com.vukasinprvulovic.application.entities.trading.data.price.bid.BID
import com.vukasinprvulovic.application.entities.trading.data.units.DailyVolume
import com.vukasinprvulovic.application.entities.trading.pair.TradingPair
import com.vukasinprvulovic.application.entities.trading.pair.TradingPairs
import com.vukasinprvulovic.configuration.di.annotations.ApplicationCoroutineDispatcher
import com.vukasinprvulovic.network.api.tickers.TickersAPI
import com.vukasinprvulovic.remote.sources.trading.pairs.ticker.TickersAPITickerProducingStrategy
import com.vukasinprvulovic.utils.kotlin.results.foldResultsSuspend
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TradingPairsRemoteSystem @Inject constructor(
    @ApplicationCoroutineDispatcher private val dispatcher: CoroutineDispatcher,
    private val tickersAPI: TickersAPI
): TradingPairsRemoteSource {

    override suspend fun fetchTradingPairsData(tradingPairs: TradingPairs): Result<TradingPairs> = withContext(dispatcher) {
        foldResultsSuspend {
            val tickersResult = tickersAPI.getTickers(tradingPairs).getOrThrow()
            Result.success(parseAPIResponse(tickersResult, tradingPairs))
        }
    }

    @VisibleForTesting
    suspend fun parseAPIResponse(response: Map<String, Any?>, tradingPairs: TradingPairs): TradingPairs {
        val tradingPairTickersToPair = tradingPairs.associateBy { it.ticker(TickersAPITickerProducingStrategy()) }
        val tradingPairsWithData = mutableListOf<TradingPair<*,*>>()
        tradingPairTickersToPair.entries.forEach {
            val (ticker, tradingPair) = it
            val tickerData = response[ticker.symbol] as? List<Any?>
            tradingPairsWithData.add(parseTickerData(tradingPair, tickerData))
        }
        return TradingPairs(tradingPairsWithData)
    }

    private suspend fun parseTickerData(tradingPair: TradingPair<*,*>, tickerData: List<Any?>?): TradingPair<*,*> {
        val bidPrice = tickerData?.get(0) as? Float ?: 0f
        val bidSize = tickerData?.get(1) as? Float ?: 0f
        val askPrice = tickerData?.get(2) as? Float ?: 0f
        val askSize = tickerData?.get(3) as? Float ?: 0f
        val dailyChange = tickerData?.get(4) as? Float ?: 0f
        val dailyChangePercentage = tickerData?.get(5) as? Float ?: 0f
        val lastPrice = tickerData?.get(6) as? Float ?: 0f
        val volume = tickerData?.get(7) as? Float ?: 0f
        val high = tickerData?.get(8) as? Float ?: 0f
        val low = tickerData?.get(9) as? Float ?: 0f

        tradingPair.tradingData.addData(
            BID(bidPrice, bidSize), ASK(askPrice, askSize), Price.Change.Daily(dailyChange, dailyChangePercentage),
            Price.LastTrade(lastPrice), DailyVolume(volume), Price.DailyMetrics.High(high), Price.DailyMetrics.Low(low)
        )
        return tradingPair
    }
}