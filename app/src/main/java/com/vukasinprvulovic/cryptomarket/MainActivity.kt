package com.vukasinprvulovic.cryptomarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.vukasinprvulovic.design.theming.CryptoMarketTheme
import com.vukasinprvulovic.scenes.marketplace.Marketplace
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CryptoMarketTheme {
                Marketplace()
            }
        }
    }
}
