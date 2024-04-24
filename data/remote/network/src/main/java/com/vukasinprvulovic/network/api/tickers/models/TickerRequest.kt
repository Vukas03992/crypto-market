package com.vukasinprvulovic.network.api.tickers.models

data class TickerRequest(
    val tickers: List<String>
) {
    fun getHttpQuery(): String {
        val list = tickers.joinToString(separator = ",")
        return "symbols=$list"
    }
}
