package com.vukasinprvulovic.network.api.tickers

import com.vukasinprvulovic.configuration.di.annotations.BitfinexBaseUrl
import com.vukasinprvulovic.network.api.tickers.models.TickerRequest
import com.vukasinprvulovic.network.integrations.ktor.Ktor
import io.ktor.client.request.get
import io.ktor.client.request.url
import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.floatOrNull
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonPrimitive
import javax.inject.Inject
import javax.inject.Singleton

interface TickersAPI {
    suspend fun getTickers(tickerRequest: TickerRequest): Result<Map<String, List<Float?>>>
}

@Singleton
class KtorTickersAPI @Inject constructor(
    private val ktor: Ktor,
    @BitfinexBaseUrl private val baseUrl: String
) : TickersAPI {
    override suspend fun getTickers(tickerRequest: TickerRequest): Result<Map<String, List<Float?>>> {
        val requestQuery = tickerRequest.getHttpQuery()
        val result = ktor.executeNetworkCall<JsonArray> { client ->
            client.get {
                url(urlString = "$baseUrl/tickers?$requestQuery")
            }
        }
        return result.map {
            val mutableMap = mutableMapOf<String, List<Float?>>()
            it.forEach { ticker ->
                val symbol = ticker.jsonArray[0].jsonPrimitive.content
                val tickerData = ticker.jsonArray.drop(1).map { it.jsonPrimitive.floatOrNull }
                mutableMap[symbol] = tickerData
            }
            mutableMap
        }
    }
}
