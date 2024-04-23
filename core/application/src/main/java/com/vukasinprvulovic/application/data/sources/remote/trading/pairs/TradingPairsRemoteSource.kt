package com.vukasinprvulovic.application.data.sources.remote.trading.pairs

import com.vukasinprvulovic.application.entities.trading.pair.TradingPairs

interface TradingPairsRemoteSource {
    suspend fun fetchTradingPairsData(tradingPairs: TradingPairs): Result<TradingPairs>
}