package com.vukasinprvulovic.network.base.errors

sealed class NetworkError(message: String? = null, cause: Throwable? = null) : Throwable(message, cause) {
    data class HttpError(val httpCode: Int, val httpMessage: String?) : NetworkError(message = "HttpCode: $httpCode, HttpMessage: $httpMessage")
    class IOError(cause: Throwable?) : NetworkError(cause = cause)
    class Timeout : NetworkError()
    class NoInternetAccess : NetworkError()
    class Unknown(cause: Throwable? = null) : NetworkError(cause = cause)
}
