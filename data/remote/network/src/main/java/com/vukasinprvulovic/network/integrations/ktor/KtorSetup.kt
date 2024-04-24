package com.vukasinprvulovic.network.integrations.ktor

import com.vukasinprvulovic.network.base.errors.NetworkError
import com.vukasinprvulovic.utils.kotlin.results.foldResultsSuspend
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.call.body
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Ktor @Inject constructor(
    engine: HttpClientEngine,
    userConfig: HttpClientConfig<out HttpClientEngineConfig>
) {
    val client = HttpClient(engine, userConfig)

    suspend inline fun <reified T> executeNetworkCall(noinline configuration: suspend (client: HttpClient) -> HttpResponse): Result<T> = foldResultsSuspend {
        val httpResponse = try {
            configuration(client)
        } catch (exception: Exception) {
            return@foldResultsSuspend when (exception) {
                is SocketTimeoutException -> Result.failure(NetworkError.Timeout())
                is IOException -> {
                    if (exception is UnknownHostException) {
                        Result.failure(NetworkError.NoInternetAccess())
                    } else Result.failure(NetworkError.IOError(exception))
                }
                else -> Result.failure(NetworkError.Unknown(exception))
            }
        }
        when(httpResponse.status.value) {
            in 200..299 -> Result.success(httpResponse.body())
            else -> Result.failure(NetworkError.HttpError(httpResponse.status.value, httpResponse.status.description))
        }
    }
}

internal val defaultHttpClientConfig = HttpClientConfig<HttpClientEngineConfig>().apply {
    expectSuccess = false
    install(Logging) {
        level = LogLevel.ALL
    }
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }
    install(DefaultRequest) {
        header(HttpHeaders.Accept, ContentType.Application.Json)
    }
}
