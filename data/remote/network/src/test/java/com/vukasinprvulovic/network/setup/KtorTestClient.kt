@file:Suppress("TestFunctionName")

package com.vukasinprvulovic.network.setup

import com.vukasinprvulovic.network.integrations.ktor.Ktor
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json

fun TestingKtor(handler: suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData): Ktor {
    return Ktor(
        MockEngine.create {
            addHandler { handler(it) }
        },
        HttpClientConfig<HttpClientEngineConfig>().apply {
            install(DefaultRequest) {
                header(HttpHeaders.Accept, ContentType.Application.Json)
            }
            install(ContentNegotiation) {
                json()
            }
        }
    )
}
