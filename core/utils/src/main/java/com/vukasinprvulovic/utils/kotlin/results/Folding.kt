package com.vukasinprvulovic.utils.kotlin.results

suspend fun <T> foldResultsSuspend(action: suspend () -> Result<T>) : Result<T> {
    return try {
        action()
    } catch (throwable: Throwable) {
        Result.failure(throwable)
    }
}

fun <T> foldResults(action: () -> Result<T>) : Result<T> {
    return try {
        action()
    } catch (throwable: Throwable) {
        Result.failure(throwable)
    }
}
