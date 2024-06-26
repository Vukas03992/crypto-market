package com.vukasinprvulovic.application.feature.cryptomarketplace.store.unit

import com.vukasinprvulovic.application.features.cryptomarketplace.result.CryptoMarketplaceResults
import com.vukasinprvulovic.application.features.cryptomarketplace.store.CryptoMarketplaceStore
import com.vukasinprvulovic.application.features.cryptomarketplace.store.InternalCryptoMarketplaceStore
import com.vukasinprvulovic.application.features.cryptomarketplace.store.actions.CryptoMarketplaceStoreAction
import com.vukasinprvulovic.application.features.cryptomarketplace.store.actions.CryptoMarketplaceStoreActionHandler
import com.vukasinprvulovic.application.features.cryptomarketplace.store.actions.CryptoMarketplaceStoreActionHandlers
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CryptoMarketplaceScoreTests {

    @Test
    fun `when store is being visited without defined actions then store visiting is without results`() = runTest {
        val store = InternalCryptoMarketplaceStore(CryptoMarketplaceStoreActionHandlers(emptySet()))
        var results: CryptoMarketplaceResults? = null
        store.visitStore(CryptoMarketplaceResults(emptySet(), emptyList()), CryptoMarketplaceStoreAction.Finish).collect {
            results = it
        }
        results.shouldBeNull()
    }

    @Test
    fun `when store is being visited with any defined action then store visiting is finished with executed action`() = runTest {
        val openActionHandler = object : CryptoMarketplaceStoreActionHandler {
            override val actionIdentifier: String = CryptoMarketplaceStoreAction.Start.identifier
            override suspend fun handle(context: CryptoMarketplaceStore.Context, emitter: FlowCollector<CryptoMarketplaceResults>) {
                context.nextAction = CryptoMarketplaceStoreAction.Finish
                emitter.emit(CryptoMarketplaceResults(setOf(OpenActionResult), emptyList()))
            }
        }
        val store = InternalCryptoMarketplaceStore(CryptoMarketplaceStoreActionHandlers(setOf(openActionHandler)))
        val results: CryptoMarketplaceResults = store.visitStore(CryptoMarketplaceResults(emptySet(), emptyList()), CryptoMarketplaceStoreAction.Start).first()
        results.data().size shouldBe 1
        results.data().toList()[0].shouldBeInstanceOf<OpenActionResult>()
    }

    data object OpenActionResult: CryptoMarketplaceResults.Data
}