package com.vukasinprvulovic.application.features.cryptomarketplace.result

data class CryptoMarketplaceResults(
    val data: List<Data>,
    val error: List<Error>
) {

    interface Data

    abstract class Error: Throwable()
}