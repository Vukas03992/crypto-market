package com.vukasinprvulovic.network.api.tickers

import com.vukasinprvulovic.application.entities.trading.pair.TradingPairs
import com.vukasinprvulovic.network.setup.TestingKtor
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import io.ktor.client.engine.mock.respond
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.runTest
import org.junit.Test

class TickersAPITests {

    @Test
    fun `when tickers API is called then it should return tickers`() = runTest {
        val ktor = TestingKtor {
            respond(
                content = ByteReadChannel(responseJsonString),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType to listOf(ContentType.Application.Json.toString()))
            )
        }
        val ktorTickersAPI = KtorTickersAPI(
            ktor = ktor,
            baseUrl = "https://api-pub.bitfinex.com/v2"
        )
        val result = ktorTickersAPI.getTickers(TradingPairs(emptyList()))
        result.isSuccess shouldBe true
        val tickers = result.getOrNull().shouldNotBeNull().shouldBeInstanceOf<Map<String, Any?>>()
        tickers.size shouldBe 3
        tickers["tBTCUSD"].shouldNotBeNull().shouldBeInstanceOf<List<Any>>().size shouldBe 10
    }
}

private val responseJsonString = """
    [
  [
    "tBTCUSD",
    66591,
    5.33263766,
    66594,
    4.28970353,
    260,
    0.00391808,
    66619,
    710.55988306,
    68158,
    65770
  ],
  [
    "tLTCUSD",
    84.78,
    931.46399083,
    84.836,
    904.62160513,
    -0.114,
    -0.00134163,
    84.857,
    6298.25195168,
    86.49,
    84.143
  ],
  [
    "tLTCBTC",
    0.001273,
    1445.77707263,
    0.0012739,
    1316.46526814,
    -0.0000111,
    -0.00864688,
    0.0012726,
    5526.75407644,
    0.0013018,
    0.00127
  ]
  ]
""".trimIndent()
