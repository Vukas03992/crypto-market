package com.vukasinprvulovic.application.feature.cryptomarketplace.store.unit.lifecycle

import com.vukasinprvulovic.application.features.cryptomarketplace.lifecycle.CryptoMarketplaceLifecycle
import com.vukasinprvulovic.application.features.cryptomarketplace.lifecycle.CryptoMarketplaceLifecycleManager
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.Test

class CryptoMarketplaceLifecycleTests {

    @Test
    fun `when lifecycle manager is created then no state changes are emitted before initialize method`() = runTest {
        val cryptoMarketplaceLifecycleManager = CryptoMarketplaceLifecycleManager()
        val lifecycleChanges = mutableListOf<CryptoMarketplaceLifecycle.State>()
        cryptoMarketplaceLifecycleManager.addLifecycleListener { state ->
            lifecycleChanges.add(state)
        }
        lifecycleChanges.shouldBeEmpty()
    }

    @Test
    fun `when initialize is executed then INITIALIZED state is emitted`() = runTest {
        val cryptoMarketplaceLifecycleManager = CryptoMarketplaceLifecycleManager()
        val lifecycleChanges = mutableListOf<CryptoMarketplaceLifecycle.State>()
        cryptoMarketplaceLifecycleManager.addLifecycleListener { state ->
            lifecycleChanges.add(state)
        }
        cryptoMarketplaceLifecycleManager.initialize()
        lifecycleChanges.shouldNotBeEmpty().first() shouldBe CryptoMarketplaceLifecycle.State.INITIALIZED(CryptoMarketplaceLifecycle.State.NOTINITIALIZED)
    }

    @Test
    fun `when initialize is not executed but start is called then start is executed after initialized is being executed`() = runTest {
        val cryptoMarketplaceLifecycleManager = CryptoMarketplaceLifecycleManager()
        val lifecycleChanges = mutableListOf<CryptoMarketplaceLifecycle.State>()
        cryptoMarketplaceLifecycleManager.addLifecycleListener { state ->
            lifecycleChanges.add(state)
        }
        cryptoMarketplaceLifecycleManager.start()
        lifecycleChanges.shouldBeEmpty()
        cryptoMarketplaceLifecycleManager.initialize()
        lifecycleChanges.shouldNotBeEmpty().size shouldBe 2
        lifecycleChanges.last() shouldBe CryptoMarketplaceLifecycle.State.STARTED(CryptoMarketplaceLifecycle.State.INITIALIZED(CryptoMarketplaceLifecycle.State.NOTINITIALIZED))
    }

    @Test
    fun `when initialize and start are not executed but resume is called then resume is executed after initialized and start are being executed`() = runTest {
        val cryptoMarketplaceLifecycleManager = CryptoMarketplaceLifecycleManager()
        val lifecycleChanges = mutableListOf<CryptoMarketplaceLifecycle.State>()
        cryptoMarketplaceLifecycleManager.addLifecycleListener { state ->
            lifecycleChanges.add(state)
        }
        cryptoMarketplaceLifecycleManager.resume()
        lifecycleChanges.shouldBeEmpty()
        cryptoMarketplaceLifecycleManager.start()
        lifecycleChanges.shouldBeEmpty()
        cryptoMarketplaceLifecycleManager.initialize()
        lifecycleChanges.shouldNotBeEmpty().size shouldBe 3
        lifecycleChanges.last() shouldBe CryptoMarketplaceLifecycle.State.RESUMED(CryptoMarketplaceLifecycle.State.STARTED(CryptoMarketplaceLifecycle.State.INITIALIZED(CryptoMarketplaceLifecycle.State.NOTINITIALIZED)))
    }
}
