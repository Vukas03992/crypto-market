@file:OptIn(ExperimentalStdlibApi::class)

package com.vukasinprvulovic.application.feature.cryptomarketplace.store.unit.refreshing

import com.vukasinprvulovic.application.features.cryptomarketplace.refreshing.CryptoMarketplaceRefresher
import com.vukasinprvulovic.application.features.cryptomarketplace.refreshing.conditions.CryptoMarketplaceRefreshingConditions
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CryptoMarketplaceRefresherTests {

    @Test
    fun `when refreshing is started then current refreshing job is not null and it is active`() = runTest {
        val refresher = CryptoMarketplaceRefresher(coroutineContext[CoroutineDispatcher]!!, CryptoMarketplaceRefreshingConditions(CryptoMarketplaceRefreshingConditions.All(emptySet())))
        refresher.internalAction = { true }
        refresher.getCurrentJob().shouldBeNull()
        refresher.start()
        advanceUntilIdle()
        refresher.getCurrentJob().shouldNotBeNull()
        refresher.stop()
    }
}
